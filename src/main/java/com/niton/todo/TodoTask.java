package com.niton.todo;

import com.niton.reactj.ReactiveObject;
import com.niton.reactj.annotation.Reactive;
import com.niton.reactj.annotation.Unreactive;

public class TodoTask extends ReactiveObject{
	public TodoTask(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	private String name;
	@Reactive("description")
	private String desc;
	private boolean done = false;

	public void setName(String name) {
		this.name = name;
		react();
	}

	public void setDesc(String desc) {
		this.desc = desc;
		react();
	}

	public void setDone(boolean done) {
		this.done = done;
		react();
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public boolean isDone() {
		return done;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("TodoTask{");
		sb.append("name='").append(name).append('\'');
		sb.append(", desc='").append(desc).append('\'');
		sb.append(", done=").append(done);
		sb.append('}');
		return sb.toString();
	}
}
