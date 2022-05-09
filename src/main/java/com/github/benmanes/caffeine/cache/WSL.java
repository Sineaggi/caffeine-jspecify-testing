// Copyright 2022 Ben Manes. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.github.benmanes.caffeine.cache;

/**
 * <em>WARNING: GENERATED CODE</em>
 *
 * <p>A cache that provides the following features:
 *
 * <ul>
 *   <li>Listening
 *   <li>WeakKeys (inherited)
 *   <li>StrongValues (inherited)
 * </ul>
 *
 * @author ben.manes@gmail.com (Ben Manes)
 */
@SuppressWarnings({"MissingOverride", "NullAway"})
class WSL<K, V> extends WS<K, V> {
  final RemovalListener<K, V> removalListener;

  WSL(Caffeine<K, V> builder, AsyncCacheLoader<? super K, V> cacheLoader, boolean async) {
    super(builder, cacheLoader, async);
    this.removalListener = builder.getRemovalListener(async);
  }

  public final RemovalListener<K, V> removalListener() {
    return removalListener;
  }

  protected final boolean hasRemovalListener() {
    return true;
  }
}
