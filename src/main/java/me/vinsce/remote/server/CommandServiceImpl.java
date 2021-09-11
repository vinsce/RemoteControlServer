package me.vinsce.remote.server;

import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.vinsce.remote.server.proto.CommandProto;
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

        final var reply = CommandProto.GenericCommandResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }


    @Override
    public void moveMouse(CommandProto.MouseMoveCommand request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        log.trace("Received MouseMoveCommand({}, {})", request.getDeltaX(), request.getDeltaY());

        final var currentLocation = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove((int) (currentLocation.getX() + request.getDeltaX()), (int) (currentLocation.getY() + request.getDeltaY()));

        final var reply = CommandProto.GenericCommandResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void clickMouse(CommandProto.MouseClickCommand request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        log.debug("Received MouseClickCommand(singleTap: {})", request.getSingleTap());

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        if (request.getSingleTap())
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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
    }
}
