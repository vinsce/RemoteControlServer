package me.vinsce.remote.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import me.vinsce.remote.server.actions.KeyPressAction;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.MediaServiceGrpc;

@Slf4j
public class MediaServiceImpl extends MediaServiceGrpc.MediaServiceImplBase {
    @Override
    public void volumeUp(Empty request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        new KeyPressAction(KeyPressAction.Key.VOLUME_UP).execute();
        genericCommandSuccess(responseObserver);
    }

    @Override
    public void volumeDown(Empty request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        new KeyPressAction(KeyPressAction.Key.VOLUME_DOWN).execute();
        genericCommandSuccess(responseObserver);
    }

    @Override
    public void mute(Empty request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        new KeyPressAction(KeyPressAction.Key.VOLUME_MUTE).execute();
        genericCommandSuccess(responseObserver);
    }

    @Override
    public void playPause(Empty request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        new KeyPressAction(KeyPressAction.Key.MEDIA_PLAY_PAUSE).execute();
        genericCommandSuccess(responseObserver);
    }

    @Override
    public void stop(Empty request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        new KeyPressAction(KeyPressAction.Key.MEDIA_STOP).execute();
        genericCommandSuccess(responseObserver);
    }

    @Override
    public void mediaNext(Empty request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        new KeyPressAction(KeyPressAction.Key.MEDIA_NEXT_TRACK).execute();
        genericCommandSuccess(responseObserver);
    }

    @Override
    public void mediaPrev(Empty request, StreamObserver<CommandProto.GenericCommandResponse> responseObserver) {
        new KeyPressAction(KeyPressAction.Key.MEDIA_PREV_TRACK).execute();
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
