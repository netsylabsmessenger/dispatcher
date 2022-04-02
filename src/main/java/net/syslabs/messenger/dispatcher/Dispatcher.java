/*
 * Copyright (c) 2019-2022 Clément Cazaud <clement.cazaud@gmail.com>,
 *                         Hamza Abidi <abidi.hamza84000@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.syslabs.messenger.dispatcher;

import net.syslabs.messenger.dispatcher.exception.DispatcherException;
import net.syslabs.messenger.dispatcher.handler.HandlerInterface;
import net.sylabs.messenger.message.MessageInterface;

import java.util.List;

public class Dispatcher implements DispatcherInterface {
    private final List<HandlerInterface> handlers;

    public Dispatcher(List<HandlerInterface> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void dispatch(MessageInterface message) throws DispatcherException {
        for (HandlerInterface handler : handlers) {
            if (!handler.supports(message)) {
                continue;
            }

            try {
                handler.handle(message, this);
            } catch (Throwable exception) {
                throw new DispatcherException(exception.getMessage(), exception);
            }
        }
    }
}
