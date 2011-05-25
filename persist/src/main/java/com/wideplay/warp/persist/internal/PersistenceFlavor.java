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
package com.wideplay.warp.persist.internal;

import com.wideplay.warp.persist.PersistenceStrategy;
import com.wideplay.warp.persist.hibernate.HibernatePersistenceStrategy;
import com.wideplay.warp.persist.jpa.JpaPersistenceStrategy;

/**
 * Enumerates the persistence engines we support out-of-the-box.
 *
 * @author Robbie Vanbrabant
 */
public enum PersistenceFlavor implements HasPersistenceStrategy {
    HIBERNATE {
        public PersistenceStrategy getPersistenceStrategy() {
            return HibernatePersistenceStrategy.builder().build();
        }
    },
    JPA {
        public PersistenceStrategy getPersistenceStrategy() {
            // 1.0 compatibility mode
            return JpaPersistenceStrategy.builder().build();
        }
    }
}
