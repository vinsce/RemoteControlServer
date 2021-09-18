package me.vinsce.remote.server;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.CommandServiceGrpc;

@Slf4j
public class CommandServiceImpl extends CommandServiceGrpc.CommandServiceImplBase {

    @Override
    public void command(CommandProto.GenericCommandRequest request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        log.debug("Received request: {}", request.getCommand());
        Responses.genericCommandSuccess(responseObserver);
    }
}
