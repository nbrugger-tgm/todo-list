package com.niton.todo;

import com.niton.reactj.ReactiveObject;
import com.niton.reactj.annotation.Reactive;
import com.niton.reactj.special.Identity;

import java.io.Serializable;

public class TodoTask extends ReactiveObject implements Serializable, Identity<String> {
	private String name;
	@Reactive("description")
	private String desc;
	private boolean done = false;

	public TodoTask(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		react();
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
		react();
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
		react();
	}

	@Override
	public String getID() {
		return name+" "+desc;
	}
}
