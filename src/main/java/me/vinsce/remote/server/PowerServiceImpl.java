package me.vinsce.remote.server;

import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.vinsce.remote.server.actions.power.RestartAction;
import me.vinsce.remote.server.actions.power.ShutdownAction;
import me.vinsce.remote.server.actions.power.SleepAction;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.PowerProto;
import me.vinsce.remote.server.proto.PowerServiceGrpc;

@Slf4j
public class PowerServiceImpl extends PowerServiceGrpc.PowerServiceImplBase {
    @SneakyThrows
    @Override
    public void powerCommand(PowerProto.PowerCommandRequest request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
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
                Responses.genericCommandFailure(responseObserver);
                return;
        }

        Responses.genericCommandSuccess(responseObserver);
    }
}
