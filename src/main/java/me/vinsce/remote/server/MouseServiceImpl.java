package me.vinsce.remote.server;

import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.MouseProto;
import me.vinsce.remote.server.proto.MouseServiceGrpc;

import java.awt.*;
import java.awt.event.InputEvent;

@Slf4j
public class MouseServiceImpl extends MouseServiceGrpc.MouseServiceImplBase {

    private final Robot robot;

    @SneakyThrows
    public MouseServiceImpl() {
        robot = new Robot();
    }

    @Override
    public void moveMouse(MouseProto.MouseMoveRequest request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        log.trace("Received MouseMoveCommand({}, {})", request.getDeltaX(), request.getDeltaY());

        final var currentLocation = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove((int) (currentLocation.getX() + request.getDeltaX()), (int) (currentLocation.getY() + request.getDeltaY()));

        Responses.genericCommandSuccess(responseObserver);
    }

    @Override
    public void mouseEvent(MouseProto.MouseEventRequest request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
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

        Responses.genericCommandSuccess(responseObserver);
    }
}
