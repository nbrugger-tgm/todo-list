package com.niton.todo.ui;

import com.niton.reactj.Reactable;
import com.niton.reactj.ReactiveBinder;
import com.niton.reactj.mvc.EventManager;
import com.niton.reactj.mvc.ReactiveView;
import com.niton.reactj.special.ReactiveList;
import com.niton.reactj.special.ReactiveListModel;
import com.niton.reactj.special.ReactiveListView;
import com.niton.todo.TodoTask;

import javax.swing.*;

public class TodoListView extends ReactiveListView<JPanel,JPanel,TodoTask>{
	private JPanel list;
	private final EventManager<String> onRemove = new EventManager<>();

	public EventManager<String> getOnRemove() {
		return onRemove;
	}

	@Override
	protected int size() {
		return list.getComponentCount();
	}

	@Override
	public void remove(JPanel todoView) {
		list.remove(todoView);
	}

	@Override
	public ReactiveView<JPanel, TodoTask> createElement(TodoTask todoTask) {
		TodoView v = new TodoView();
		v.setData(todoTask);
		v.getOnRemove().listen(onRemove::fire);
		return v;
	}

	@Override
	public void addAt(JPanel jPanel, int i) {
		list.add(jPanel,i);
	}

	@Override
	public void refresh() {
		list.repaint();
		list.validate();
	}

	@Override
	public void removeFrom(int i) {
		list.remove(i);
	}

	@Override
	protected JPanel createView() {
		list = new JPanel();
		list.setLayout(new BoxLayout(list,BoxLayout.Y_AXIS));
		return list;
	}
}
