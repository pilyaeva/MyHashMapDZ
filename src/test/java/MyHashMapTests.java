import org.junit.jupiter.api.Test;
import org.myhashmap.MyHashMap;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTests {

    @Test
    void put_shouldAddNewEntryAndReturnNull() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // when
        var result = map.put("A", 1);

        // then
        assertNull(result);
        assertEquals(1, map.size());
        assertEquals(1, map.get("A"));
    }

    @Test
    void put_shouldUpdateExistingKeyAndReturnOldValue() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);

        // when
        var oldValue = map.put("A", 100);

        // then
        assertEquals(1, oldValue);
        assertEquals(100, map.get("A"));
        assertEquals(1, map.size());
    }

    @Test
    void get_shouldReturnValueForExistingKey() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("key", 42);

        // when
        var result = map.get("key");

        // then
        assertEquals(42, result);
    }

    @Test
    void get_shouldReturnNullForMissingKey() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);

        // when
        var result = map.get("B");

        // then
        assertNull(result);
    }

    @Test
    void remove_shouldDeleteEntryAndReturnOldValue() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);
        map.put("B", 2);

        // when
        var removed = map.remove("A");

        // then
        assertEquals(1, removed);
        assertEquals(1, map.size());
        assertNull(map.get("A"));
    }

    @Test
    void remove_shouldReturnNullIfKeyNotFound() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);

        // when
        var result = map.remove("X");

        // then
        assertNull(result);
        assertEquals(1, map.size());
    }

    @Test
    void containsKey_shouldReturnTrueForExistingKey() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);

        // when
        var result = map.containsKey("A");

        // then
        assertTrue(result);
    }

    @Test
    void containsKey_shouldReturnFalseForMissingKey() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // when
        var result = map.containsKey("X");

        // then
        assertFalse(result);
    }

    @Test
    void containsValue_shouldReturnTrueForExistingValue() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 42);

        // when
        var result = map.containsValue(42);

        // then
        assertTrue(result);
    }

    @Test
    void containsValue_shouldReturnFalseForMissingValue() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);

        // when
        var result = map.containsValue(999);

        // then
        assertFalse(result);
    }

    @Test
    void putAll_shouldCopyAllEntriesFromAnotherMap() {
        // given
        MyHashMap<String, Integer> map1 = new MyHashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);

        MyHashMap<String, Integer> map2 = new MyHashMap<>();
        map2.put("C", 3);
        map2.put("D", 4);

        // when
        map1.putAll(map2);

        // then
        assertEquals(4, map1.size());
        assertEquals(3, map1.get("C"));
        assertEquals(4, map1.get("D"));
    }

    @Test
    void putAll_shouldOverwriteExistingKeys() {
        // given
        MyHashMap<String, Integer> map1 = new MyHashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);

        MyHashMap<String, Integer> map2 = new MyHashMap<>();
        map2.put("B", 200);

        // when
        map1.putAll(map2);

        // then
        assertEquals(200, map1.get("B"));
        assertEquals(2, map1.size());
    }

    @Test
    void clear_shouldRemoveAllEntries() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);
        map.put("B", 2);

        // when
        map.clear();

        // then
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
        assertNull(map.get("A"));
    }

    @Test
    void keySet_shouldReturnAllKeys() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        // when
        var keys = map.keySet();

        // then
        assertEquals(3, keys.size());
        assertTrue(keys.contains("A"));
        assertTrue(keys.contains("B"));
        assertTrue(keys.contains("C"));
    }

    @Test
    void values_shouldReturnAllValuesIncludingDuplicates() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 10);
        map.put("B", 20);
        map.put("C", 10);

        // when
        var values = map.values();

        // then
        assertEquals(3, values.size());
        assertTrue(values.contains(10));
        assertTrue(values.contains(20));
        // Проверяем, что дубликат не потерялся
        var count = 0;
        for (Integer v : values) {
            if (v == 10) {
                count++;
            }
        }
        assertEquals(2, count);
    }

    @Test
    void entrySet_shouldReturnAllEntries() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);
        map.put("B", 2);

        // when
        var entries = map.entrySet();

        // then
        assertEquals(2, entries.size());
        var foundA = false;
        var foundB = false;
        for (Map.Entry<String, Integer> e : entries) {
            if ("A".equals(e.getKey()) && e.getValue() == 1) {
                foundA = true;
            }
            if ("B".equals(e.getKey()) && e.getValue() == 2) {
                foundB = true;
            }
        }
        assertTrue(foundA);
        assertTrue(foundB);
    }

    @Test
    void resize_shouldWorkWhenAddingManyElements() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        var elementsCount = 20;

        // when
        for (int i = 0; i < elementsCount; i++) {
            map.put("key" + i, i);
        }

        // then
        assertEquals(elementsCount, map.size());
        for (int i = 0; i < elementsCount; i++) {
            assertEquals(i, map.get("key" + i));
        }
    }

    @Test
    void nullKey_shouldBeSupported() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // when
        map.put(null, 100);
        var result = map.get(null);

        // then
        assertEquals(100, result);
        assertTrue(map.containsKey(null));
    }

    @Test
    void nullValue_shouldBeSupported() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // when
        map.put("A", null);
        var result = map.get("A");

        // then
        assertNull(result);
        assertTrue(map.containsKey("A"));
    }

    @Test
    void isEmpty_shouldReturnTrueForEmptyMap() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // when
        var result = map.isEmpty();

        // then
        assertTrue(result);
    }

    @Test
    void isEmpty_shouldReturnFalseAfterPut() {
        // given
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("A", 1);

        // when
        var result = map.isEmpty();

        // then
        assertFalse(result);
    }
}