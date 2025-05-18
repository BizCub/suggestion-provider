package com.bizcub.suggestionProvider.mixin;

import net.minecraft.command.CommandSource;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.Consumer;
import java.util.function.Function;

@Mixin(CommandSource.class)
public interface SuggestionProviderMixin {
    /**
     * @author BizCub
     * @reason Inspired by the original Suggestion Provider Fix mod by Harleyoc1
     */
    @Overwrite
    static <T> void forEachMatching(Iterable<T> iterable, String string, Function<T, Identifier> function, Consumer<T> consumer) {
        boolean inputContainsColon = string.indexOf(58) > -1;
        for (T resource : iterable) {
            Identifier identifier = function.apply(resource);
            if (inputContainsColon) {
                String identifierString = identifier.toString();

                if (CommandSource.shouldSuggest(string, identifierString)) {
                    consumer.accept(resource);
                }
            } else if (CommandSource.shouldSuggest(string, identifier.getNamespace()) || CommandSource.shouldSuggest(string, identifier.getPath())) {
                consumer.accept(resource);
            }
        }
    }
}
