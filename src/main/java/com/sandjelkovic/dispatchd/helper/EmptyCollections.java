package com.sandjelkovic.dispatchd.helper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmptyCollections {
	public static <T> List<T> list() {
		return new ArrayList<T>();
	}

	public static <T, R> Map<T, R> map() {
		return new HashMap<>();
	}

	public static Set set() {
		return new HashSet<>();
	}

	public static <T> List<T> emptyIfNull(List<T> list) {
		return (list != null) ? list : list();
	}

	public static <T, R> Map<T, R> emptyIfNull(Map<T, R> map) {
		return (map != null) ? map : map();
	}
}
