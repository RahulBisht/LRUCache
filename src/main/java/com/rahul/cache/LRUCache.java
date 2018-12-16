package com.rahul.cache;

import java.util.HashMap;

public class LRUCache {

	private static final int LRU_SIZE = 4;

	private HashMap<Integer, Entry> map;
	private Entry                   start;
	private Entry                   end;

	public LRUCache() {

		map = new HashMap<Integer, Entry>();
	}

	public int get(int key) {

		if (map.containsKey(key)) {
			Entry entry = map.get(key);
			removeEntryFromLinkedList(entry);
			addAtStartOfLinkedList(entry);
			return entry.value;
		}
		return -1;
	}

	public void set(int key, int value) {

		if (map.containsKey(key)) {
			Entry entry = map.get(key);
			entry.value = value;
			removeEntryFromLinkedList(entry);
			addAtStartOfLinkedList(entry);
		} else {
			Entry newnode = new Entry();
			newnode.previous = null;
			newnode.next = null;
			newnode.value = value;
			newnode.key = key;
			if (map.size() > LRU_SIZE) {
				map.remove(end.key);
				removeEntryFromLinkedList(end);
				addAtStartOfLinkedList(newnode);

			} else {
				addAtStartOfLinkedList(newnode);
			}

			map.put(key, newnode);
		}
	}

	private void addAtStartOfLinkedList(Entry node) {

		node.next = start;
		node.previous = null;
		if (start != null) {
			start.previous = node;
		}
		start = node;
		if (end == null) {
			end = start;
		}
	}

	private void removeEntryFromLinkedList(Entry node) {

		if (node.previous != null) {
			node.previous.next = node.next;
		} else {
			start = node.next;
		}

		if (node.next != null) {
			node.next.previous = node.previous;
		} else {
			end = node.previous;
		}
	}

	public static void main(String[] args) {

		LRUCache lrucache = new LRUCache();
		lrucache.set(10, 12);
		lrucache.set(11, 15);
		lrucache.set(12, 99);
		lrucache.set(13, 22);
		lrucache.set(14, 55);
		lrucache.set(15, 11);
		lrucache.set(12, 67);

		System.out.println(lrucache.get(10));
		System.out.println(lrucache.get(11));
		System.out.println(lrucache.get(15));

	}
}
