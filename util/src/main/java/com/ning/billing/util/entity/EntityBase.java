/*
 * Copyright 2010-2011 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.util.entity;

import java.util.UUID;

import org.joda.time.DateTime;

public abstract class EntityBase implements Entity {

    protected final UUID id;
    protected final DateTime createdDate;
    protected final DateTime updatedDate;

    // used to hydrate objects
    public EntityBase(final UUID id) {
        this(id, null, null);
    }

    // used to create new objects
    public EntityBase() {
        this(UUID.randomUUID(), null, null);
    }

    public EntityBase(final UUID id, final DateTime createdDate, final DateTime updatedDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public EntityBase(final EntityBase target) {
        this.id = UUID.randomUUID();
        this.createdDate = target.getCreatedDate();
        this.updatedDate = target.getUpdatedDate();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public DateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public DateTime getUpdatedDate() {
        return updatedDate;
    }
}
