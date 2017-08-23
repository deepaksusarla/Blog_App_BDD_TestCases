package com.blog.library;

public enum ResourceType {
	OBJECTREPOSITORY{
	    @Override
	    public String toString() {
	      return "ObjectRepository";
	    }
	},
	TESTDATA{
	    @Override
	    public String toString() {
	      return "TestData";
	    }
	}
}
