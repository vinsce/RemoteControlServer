package me.vinsce.remote.server;

import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.vinsce.remote.server.actions.power.RestartAction;
import me.vinsce.remote.server.actions.power.ShutdownAction;
import me.vinsce.remote.server.actions.power.SleepAction;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.CommandProto.PowerCommand;
import me.vinsce.remote.server.proto.CommandServiceGrpc;

import java.awt.*;
import java.awt.event.InputEvent;

@Slf4j
public class CommandServiceImpl extends CommandServiceGrpc.CommandServiceImplBase {

    private final Robot robot;

    @SneakyThrows
    public CommandServiceImpl() {
        robot = new Robot();
    }

    @Override
    public void runCommand(CommandProto.GenericCommand request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        log.debug("Received request: {}", request.getCommand());
        genericCommandSuccess(responseObserver);
    }


    @Override
    public void moveMouse(CommandProto.MouseMoveCommand request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        log.trace("Received MouseMoveCommand({}, {})", request.getDeltaX(), request.getDeltaY());

        final var currentLocation = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove((int) (currentLocation.getX() + request.getDeltaX()), (int) (currentLocation.getY() + request.getDeltaY()));

        genericCommandSuccess(responseObserver);
    }

    @Override
    public void clickMouse(CommandProto.MouseClickCommand request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        log.debug("Received MouseClickCommand(singleTap: {})", request.getSingleTap());

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        if (request.getSingleTap())
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        genericCommandSuccess(responseObserver);
    }

    @Override
    public void mouseEvent(CommandProto.MouseEventCommand request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        log.debug("Received mouse event: {}", request.getEventType());

        switch (request.getEventType()) {
            case SINGLE_TAP:
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                break;
            case DOUBLE_TAP:
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                break;
            case LONG_PRESS:
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                break;
            case SINGLE_TAP_UP:
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                break;
            case RIGHT_TAP:
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                break;
        }

        genericCommandSuccess(responseObserver);
    }

    @SneakyThrows
    @Override
    public void runPowerCommand(PowerCommand request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        log.debug("Received power action: {}", request.getAction());

        switch (request.getAction()) {
            case SHUTDOWN:
                new ShutdownAction().execute();
                break;
            case RESTART:
                new RestartAction().execute();
                break;
            case SLEEP:
                new SleepAction().execute();
                break;
            default:
                genericCommandFailure(responseObserver);
                return;
        }

        genericCommandSuccess(responseObserver);
    }

    private static void genericCommandResponse(StreamObserver<CommandProto.GenericCommandResponse> responseObserver, boolean success) {
        final var reply = CommandProto.GenericCommandResponse.newBuilder()
                .setSuccess(success)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    private static void genericCommandSuccess(StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        genericCommandResponse(responseObserver, true);
    }

    private static void genericCommandFailure(StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        genericCommandResponse(responseObserver, false);
    }

}
