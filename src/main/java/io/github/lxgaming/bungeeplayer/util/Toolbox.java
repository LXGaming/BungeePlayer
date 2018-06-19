/*
 * Copyright 2018 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.bungeeplayer.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.netty.ChannelWrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Toolbox {
    
    public static ComponentBuilder getTextPrefix() {
        ComponentBuilder componentBuilder = new ComponentBuilder("");
        componentBuilder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, getPluginInformation().create()));
        componentBuilder.append("[" + Reference.PLUGIN_NAME + "]").bold(true).color(ChatColor.BLUE);
        componentBuilder.append(" ", ComponentBuilder.FormatRetention.NONE);
        return componentBuilder;
    }
    
    public static ComponentBuilder getPluginInformation() {
        ComponentBuilder componentBuilder = new ComponentBuilder("")
                .append(Reference.PLUGIN_NAME).color(ChatColor.BLUE).bold(true).append("\n")
                .append("    Version: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY).append(Reference.PLUGIN_VERSION).color(ChatColor.WHITE).append("\n")
                .append("    Authors: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY).append(Reference.AUTHORS).color(ChatColor.WHITE).append("\n")
                .append("    Source: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY).append(getURLClickEvent(Reference.SOURCE).create()).append("\n")
                .append("    Website: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY).append(getURLClickEvent(Reference.WEBSITE).create());
        return componentBuilder;
    }
    
    public static ComponentBuilder getURLClickEvent(String url) {
        ComponentBuilder componentBuilder = new ComponentBuilder("");
        componentBuilder.event(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        componentBuilder.append(url).color(ChatColor.BLUE);
        componentBuilder.append(" ", ComponentBuilder.FormatRetention.NONE);
        return componentBuilder;
    }
    
    public static <T> Optional<ChannelWrapper> getChannelWrapper(Class<? extends T> classOfT, T instance) {
        try {
            for (Field field : classOfT.getDeclaredFields()) {
                if (!ChannelWrapper.class.isAssignableFrom(field.getType())) {
                    continue;
                }
                
                field.setAccessible(true);
                Object channelWrapper = field.get(instance);
                if (channelWrapper != null) {
                    return Optional.of((ChannelWrapper) channelWrapper);
                }
                
                return Optional.empty();
            }
            
            throw new NoSuchFieldException();
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
    
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) throws NullPointerException {
        Objects.requireNonNull(elements);
        return Stream.of(elements).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }
    
    @SafeVarargs
    public static <E> HashSet<E> newHashSet(E... elements) throws NullPointerException {
        Objects.requireNonNull(elements);
        return Stream.of(elements).collect(Collectors.toCollection(HashSet::new));
    }
}