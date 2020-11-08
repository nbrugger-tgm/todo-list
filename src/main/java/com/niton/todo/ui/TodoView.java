package com.niton.todo.ui;

import com.niton.reactj.ReactiveBinder;
import com.niton.reactj.ReactiveComponent;
import com.niton.reactj.annotation.Reactive;
import com.niton.todo.TodoController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

public class TodoView extends JPanel implements ReactiveComponent<TodoController> {

	private JEditorPane descriptionEditor;
	private JCheckBox checkBox;
	private JLabel nameLabel;

	public TodoView() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 5.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		nameLabel = new JLabel("New label");
		nameLabel.setFont(new Font("Roboto", Font.BOLD, 24));
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(5, 5, 5, 0);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 0;
		add(nameLabel, gbc_nameLabel);
		
		checkBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.gridheight = 2;
		gbc_checkBox.insets = new Insets(5, 5, 0, 5);
		gbc_checkBox.gridx = 0;
		gbc_checkBox.gridy = 0;
		add(checkBox, gbc_checkBox);
		
		descriptionEditor = new JEditorPane();
		descriptionEditor.setBackground(UIManager.getColor("Panel.background"));
		GridBagConstraints gbc_descriptionEditor = new GridBagConstraints();
		gbc_descriptionEditor.insets = new Insets(5, 0, 5, 5);
		gbc_descriptionEditor.fill = GridBagConstraints.BOTH;
		gbc_descriptionEditor.gridx = 1;
		gbc_descriptionEditor.gridy = 1;
		add(descriptionEditor, gbc_descriptionEditor);
	}

	@Override
	public void createBindings(ReactiveBinder reactiveBinder) {
		reactiveBinder.bindEdit("done", checkBox::setSelected,checkBox::isSelected);
		//reactiveBinder.bind("name",nameLabel::setText);
		reactiveBinder.bind("done",nameLabel::setText,done -> Boolean.toString((Boolean) done));
		reactiveBinder.bindEdit("description",descriptionEditor::setText,descriptionEditor::getText);
		descriptionEditor.addInputMethodListener(new InputMethodListener() {
			@Override
			public void inputMethodTextChanged(InputMethodEvent event) {
				reactiveBinder.react(event);
			}

			@Override
			public void caretPositionChanged(InputMethodEvent event) {}
		});
		checkBox.addActionListener(reactiveBinder::react);

		reactiveBinder.bind("done",descriptionEditor::setBackground,bool->(boolean)bool ? Color.GREEN : Color.RED);
	}

	@Override
	public void registerListeners(TodoController o) {}



	@Reactive("done")
	public void changeColorIfDone(boolean done){
		System.out.println("Change color to : "+done);
		if(done)
			setBackground(Color.green);
		else
			setBackground(Color.red);
		repaint();
	}
}
