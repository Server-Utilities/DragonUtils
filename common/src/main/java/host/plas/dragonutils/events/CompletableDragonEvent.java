package host.plas.dragonutils.events;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Getter @Setter
public abstract class CompletableDragonEvent<E extends CompletableDragonEvent<E>> extends DragonEvent {
    private Consumer<E> completion;

    public CompletableDragonEvent(Consumer<E> completion) {
        this.completion = completion;
    }

    public void accept() {
        if (! isCancelled() && ! isCompleted()) {
            completion.accept((E) this);
            setCompleted(true);
        }
    }

    public void acceptAsync() {
        CompletableFuture.runAsync(this::accept);
    }
}
