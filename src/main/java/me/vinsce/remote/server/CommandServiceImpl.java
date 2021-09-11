package me.vinsce.remote.server;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.CommandServiceGrpc;

@Slf4j
public class CommandServiceImpl extends CommandServiceGrpc.CommandServiceImplBase {

    @Override
    public void runCommand(CommandProto.GenericCommand request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        log.info("Received request: {}", request.getCommand());

        final var reply = CommandProto.GenericCommandResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
