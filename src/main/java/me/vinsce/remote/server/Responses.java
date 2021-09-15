package me.vinsce.remote.server;

import io.grpc.stub.StreamObserver;
import me.vinsce.remote.server.proto.CommandProto;

/**
 * Helper methods for building responses
 */
public final class Responses {

    public static void genericCommandResponse(StreamObserver<CommandProto.GenericCommandResponse> responseObserver, boolean success) {
        final var reply = CommandProto.GenericCommandResponse.newBuilder()
                .setSuccess(success)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    public static void genericCommandSuccess(StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        genericCommandResponse(responseObserver, true);
    }

    public static void genericCommandFailure(StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        genericCommandResponse(responseObserver, false);
    }
}
