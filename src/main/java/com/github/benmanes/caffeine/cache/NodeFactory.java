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

import com.github.benmanes.caffeine.cache.References.WeakKeyReference;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.ref.ReferenceQueue;

/**
 * <em>WARNING: GENERATED CODE</em>
 *
 * <p>A factory for cache nodes optimized for a particular configuration.
 *
 * @author ben.manes@gmail.com (Ben Manes)
 */
interface NodeFactory<K, V> {
  String KEY = "key";

  String VALUE = "value";

  String ACCESS_TIME = "accessTime";

  String WRITE_TIME = "writeTime";

  Object RETIRED_STRONG_KEY = new Object();

  Object DEAD_STRONG_KEY = new Object();

  WeakKeyReference<Object> RETIRED_WEAK_KEY = new WeakKeyReference<Object>(null, null);

  WeakKeyReference<Object> DEAD_WEAK_KEY = new WeakKeyReference<Object>(null, null);

  MethodType FACTORY = MethodType.methodType(void.class);

  MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

  /** Returns a node optimized for the specified features. */
  Node<K, V> newNode(
      K key,
      ReferenceQueue<K> keyReferenceQueue,
      V value,
      ReferenceQueue<V> valueReferenceQueue,
      int weight,
      long now);

  /** Returns a node optimized for the specified features. */
  Node<K, V> newNode(
      Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now);

  /**
   * Returns a key suitable for inserting into the cache. If the cache holds keys strongly then the
   * key is returned. If the cache holds keys weakly then a {@link java.lang.ref.Reference<K>}
   * holding the key argument is returned.
   */
  default Object newReferenceKey(K key, ReferenceQueue<K> referenceQueue) {
    return key;
  }

  /**
   * Returns a key suitable for looking up an entry in the cache. If the cache holds keys strongly
   * then the key is returned. If the cache holds keys weakly then a {@link
   * References.LookupKeyReference} holding the key argument is
   * returned.
   */
  default Object newLookupKey(Object key) {
    return key;
  }

  /** Returns a factory optimized for the specified features. */
  static <K, V> NodeFactory<K, V> newFactory(Caffeine<K, V> builder, boolean isAsync) {
    if (builder.interner) {
      return new Interned<>();
    }
    StringBuilder sb = new StringBuilder("com.github.benmanes.caffeine.cache.");
    if (builder.isStrongKeys()) {
      sb.append('P');
    } else {
      sb.append('F');
    }
    if (builder.isStrongValues()) {
      sb.append('S');
    } else if (builder.isWeakValues()) {
      sb.append('W');
    } else {
      sb.append('D');
    }
    if (builder.expiresVariable()) {
      if (builder.refreshAfterWrite()) {
        sb.append('A');
        if (builder.evicts()) {
          sb.append('W');
        }
      } else {
        sb.append('W');
      }
    } else {
      if (builder.expiresAfterAccess()) {
        sb.append('A');
      }
      if (builder.expiresAfterWrite()) {
        sb.append('W');
      }
    }
    if (builder.refreshAfterWrite()) {
      sb.append('R');
    }
    if (builder.evicts()) {
      sb.append('M');
      if (isAsync || (builder.isWeighted() && (builder.weigher != Weigher.singletonWeigher()))) {
        sb.append('W');
      } else {
        sb.append('S');
      }
    }
    try {
      Class<?> clazz = Class.forName(sb.toString());
      MethodHandle handle = LOOKUP.findConstructor(clazz, FACTORY);
      return (NodeFactory<K, V>) handle.invoke();
    } catch (RuntimeException | Error e) {
      throw e;
    } catch (Throwable t) {
      throw new IllegalStateException(sb.toString(), t);
    }
  }

  /** Returns whether this factory supports weak values. */
  default boolean weakValues() {
    return false;
  }

  /** Returns whether this factory supports soft values. */
  default boolean softValues() {
    return false;
  }
}
