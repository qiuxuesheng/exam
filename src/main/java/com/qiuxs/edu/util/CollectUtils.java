/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.qiuxs.edu.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.Transformer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>
 * CollectUtils class.
 * </p>
 *
 * @author chaostone
 * @version $Id: $
 */
public final class CollectUtils {

  /**
   * <p>
   * newArrayList.
   * </p>
   *
   * @param <E> a E object.
   * @return a {@link List} object.
   */
  public static <E> List<E> newArrayList() {
    return new ArrayList<E>();
  }

  /**
   * <p>
   * newArrayList.
   * </p>
   *
   * @param initialCapacity a int.
   * @param <E> a E object.
   * @return a {@link List} object.
   */
  public static <E> List<E> newArrayList(int initialCapacity) {
    return new ArrayList<E>(initialCapacity);
  }

  /**
   * <p>
   * newArrayList.
   * </p>
   *
   * @param c a {@link Collection} object.
   * @param <E> a E object.
   * @return a {@link List} object.
   */
  public static <E> List<E> newArrayList(Collection<? extends E> c) {
    return new ArrayList<E>(c);
  }

  /**
   * <p>
   * newArrayList.
   * </p>
   *
   * @param values a E object.
   * @param <E> a E object.
   * @return a {@link List} object.
   */
  public static <E> List<E> newArrayList(E... values) {
    List<E> list = new ArrayList<E>(values.length);
    for (E e : values) {
      list.add(e);
    }
    return list;
  }

  /**
   * 将一个集合按照固定大小查分成若干个集合。
   *
   * @param list a {@link List} object.
   * @param count a int.
   * @param <T> a T object.
   * @return a {@link List} object.
   */
  public static <T> List<List<T>> split(final List<T> list, final int count) {
    List<List<T>> subIdLists = CollectUtils.newArrayList();
    if (list.size() < count) {
      subIdLists.add(list);
    } else {
      int i = 0;
      while (i < list.size()) {
        int end = i + count;
        if (end > list.size()) {
          end = list.size();
        }
        subIdLists.add(list.subList(i, end));
        i += count;
      }
    }
    return subIdLists;
  }

  /**
   * <p>
   * newHashMap.
   * </p>
   *
   * @param <K> a K object.
   * @param <V> a V object.
   * @return a {@link Map} object.
   */
  public static <K, V> Map<K, V> newHashMap() {
    return new HashMap<K, V>();
  }


  /**
   * <p>
   * newConcurrentHashMap.
   * </p>
   *
   * @param <K> a K object.
   * @param <V> a V object.
   * @return a {@link Map} object.
   */
  public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
    return new ConcurrentHashMap<K, V>();
  }

  /**
   * <p>
   * newConcurrentLinkedQueue.
   * </p>
   *
   * @param <E> a E object.
   * @return a {@link Queue} object.
   */
  public static <E> Queue<E> newConcurrentLinkedQueue() {
    return new ConcurrentLinkedQueue<E>();
  }

  /**
   * <p>
   * newHashMap.
   * </p>
   *
   * @param m a {@link Map} object.
   * @param <K> a K object.
   * @param <V> a V object.
   * @return a {@link Map} object.
   */
  public static <K, V> Map<K, V> newHashMap(Map<? extends K, ? extends V> m) {
    return new HashMap<K, V>(m);
  }

  /**
   * <p>
   * newLinkedHashMap.
   * </p>
   *
   * @param m a {@link Map} object.
   * @param <K> a K object.
   * @param <V> a V object.
   * @return a {@link Map} object.
   */
  public static <K, V> Map<K, V> newLinkedHashMap(Map<? extends K, ? extends V> m) {
    return new LinkedHashMap<K, V>(m);
  }

  /**
   * <p>
   * newLinkedHashMap.
   * </p>
   *
   * @param size a int.
   * @param <K> a K object.
   * @param <V> a V object.
   * @return a {@link Map} object.
   */
  public static <K, V> Map<K, V> newLinkedHashMap(int size) {
    return new LinkedHashMap<K, V>(size);
  }

  /**
   * <p>
   * newHashSet.
   * </p>
   *
   * @param <E> a E object.
   * @return a {@link Set} object.
   */
  public static <E> Set<E> newHashSet() {
    return new HashSet<E>();
  }

  /**
   * <p>
   * newHashSet.
   * </p>
   *
   * @param values a E object.
   * @param <E> a E object.
   * @return a {@link Set} object.
   */
  public static <E> Set<E> newHashSet(E... values) {
    Set<E> set = new HashSet<E>(values.length);
    for (E e : values) {
      set.add(e);
    }
    return set;
  }

  /**
   * <p>
   * newHashSet.
   * </p>
   *
   * @param c a {@link Collection} object.
   * @param <E> a E object.
   * @return a {@link Set} object.
   */
  public static <E> Set<E> newHashSet(Collection<? extends E> c) {
    return new HashSet<E>(c);
  }

  /**
   * <p>
   * convertToMap.
   * </p>
   *
   * @param coll a {@link Collection} object.
   * @param keyProperty a {@link String} object.
   * @return a {@link Map} object.
   */
  public static Map<?, ?> convertToMap(Collection<?> coll, String keyProperty) {
    Map<Object, Object> map = newHashMap();
    for (Object obj : coll) {
      Object key = null;
      try {
        key = PropertyUtils.getProperty(obj, keyProperty);
      } catch (Exception e) {
        e.printStackTrace();
      }
      map.put(key, obj);
    }
    return map;
  }

  /**
   * <p>
   * toMap.
   * </p>
   *
   * @param wordMappings an array of {@link String} objects.
   * @return a {@link Map} object.
   */
  public static Map<String, String> toMap(String[]... wordMappings) {
    Map<String, String> mappings = new HashMap<String, String>();
    for (int i = 0; i < wordMappings.length; i++) {
      String singular = wordMappings[i][0];
      String plural = wordMappings[i][1];
      mappings.put(singular, plural);
    }
    return mappings;
  }

  /**
   * Null-safe check if the specified collection is empty.
   * <p>
   * Null returns true.
   *
   * @param coll the collection to check, may be null
   * @return true if empty or null
   * @since 3.1
   */
  public static boolean isEmpty(Collection<?> coll) {
    return (coll == null || coll.isEmpty());
  }

  /**
   * Null-safe check if the specified collection is not empty.
   * <p>
   * Null returns false.
   *
   * @param coll the collection to check, may be null
   * @return true if non-null and non-empty
   * @since 3.1
   */
  public static boolean isNotEmpty(Collection<?> coll) {
    return null != coll && !coll.isEmpty();
  }

  public static <T> List<T> union(List<T> first, List<T> second) {
    Map<T, Integer> mapa = getCardinalityMap(first), mapb = getCardinalityMap(second);
    Set<T> elts = new HashSet<T>(first);
    elts.addAll(second);
    List<T> list = newArrayList();
    for (T obj : elts)
      for (int i = 0, m = Math.max(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; i++)
        list.add(obj);
    return list;

  }

  public static <T> Map<T, Integer> getCardinalityMap(final List<T> coll) {
    Map<T, Integer> count = newHashMap();
    for (Iterator<T> it = coll.iterator(); it.hasNext();) {
      T obj = it.next();
      Integer c = (count.get(obj));
      if (c == null) count.put(obj, Integer.valueOf(1));
      else count.put(obj, new Integer(c.intValue() + 1));
    }
    return count;
  }

  private static final <T> int getFreq(final T obj, final Map<T, Integer> freqMap) {
    Integer count = freqMap.get(obj);
    return (count != null) ? count.intValue() : 0;
  }

  public static <T> List<T> intersection(List<T> first, List<T> second) {
    List<T> list = CollectUtils.newArrayList();
    Map<T, Integer> mapa = getCardinalityMap(first), mapb = getCardinalityMap(second);
    Set<T> elts = new HashSet<T>(first);
    elts.addAll(second);
    for (T obj : elts)
      for (int i = 0, m = Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; i++)
        list.add(obj);
    return list;
  }

  public static <T> Set<T> intersection(Set<T> first, Set<T> second) {
    Set<T> elts = CollectUtils.newHashSet();
    for (T obj : first)
      if (second.contains(obj)) elts.add(obj);
    return elts;
  }

  public static <T> List<T> subtract(List<T> first, List<T> second) {
    List<T> list = newArrayList(first);
    for (T t : second)
      list.remove(t);
    return list;
  }

  public static <T> Set<T> subtract(final Set<T> a, final Set<T> b) {
    Set<T> set = CollectUtils.newHashSet(a);
    set.removeAll(b);
    return set;
  }

}
