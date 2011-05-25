/**
 * Copyright (C) 2008 Wideplay Interactive.
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
package com.wideplay.warp.persist;

import com.wideplay.warp.persist.spi.PersistenceConfiguration;
import com.wideplay.warp.persist.spi.PersistenceModule;

/**
 * Strategy for hooking persistence strategies into warp-persist.
 * @author Robbie Vanbrabant
 */
public interface PersistenceStrategy {
    /**
     * Produces a {@link com.wideplay.warp.persist.spi.PersistenceModule}
     * from the given configuration.
     * 
     * @param config all configuration gathered through the public fluent interface API
     * @return the {@link com.wideplay.warp.persist.spi.PersistenceModule} containing all
     *         that's needed for configuring this {@code PersistenceStrategy}
     */
    PersistenceModule getBindings(PersistenceConfiguration config);
}
