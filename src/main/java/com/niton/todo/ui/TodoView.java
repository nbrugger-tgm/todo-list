package com.niton.todo.ui;

import com.niton.reactj.ReactiveBinder;
import com.niton.reactj.annotation.Reactive;
import com.niton.reactj.mvc.EventManager;
import com.niton.reactj.mvc.Listener;
import com.niton.reactj.mvc.ReactiveView;
import com.niton.reactj.swing.JRComponent;
import com.niton.reactj.swing.components.JRButton;
import com.niton.reactj.swing.components.JRCheckBox;
import com.niton.reactj.swing.components.JREditorPane;
import com.niton.reactj.swing.components.JRLabel;
import com.niton.todo.TodoTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.font.TextAttribute;
import java.util.Map;

public class TodoView extends JRComponent<TodoTask> {

	//JR indicates that it is the reactive equivalent to the swing component JLabel = JRLabel
	private       JREditorPane         descriptionEditor;
	private       JRCheckBox           checkBox;
	private       JRLabel              nameLabel;
	private       Font                 normal;
	private       Font                 striked;
	private final EventManager<String> onRemove = new EventManager<>();
	private       JRButton             removeButton;

	public EventManager<String> getOnRemove() {
		return onRemove;
	}

	public JPanel createView(){
		JPanel panel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 5.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		nameLabel = new JRLabel();
		nameLabel.setFont(new Font("Roboto", Font.BOLD, 24));
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(5, 5, 5, 0);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 0;
		panel.add(nameLabel, gbc_nameLabel);
		
		checkBox = new JRCheckBox();
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.gridheight = 2;
		gbc_checkBox.insets = new Insets(5, 5, 0, 5);
		gbc_checkBox.gridx = 0;
		gbc_checkBox.gridy = 0;
		panel.add(checkBox, gbc_checkBox);
		
		descriptionEditor = new JREditorPane();
		descriptionEditor.setBackground(UIManager.getColor("Panel.background"));
		GridBagConstraints gbc_descriptionEditor = new GridBagConstraints();
		gbc_descriptionEditor.insets = new Insets(5, 0, 5, 5);
		gbc_descriptionEditor.fill = GridBagConstraints.BOTH;
		gbc_descriptionEditor.gridx = 1;
		gbc_descriptionEditor.gridy = 1;
		panel.add(descriptionEditor, gbc_descriptionEditor);

		removeButton = new JRButton();
		removeButton.setText("X");
		panel.add(removeButton);
		return panel;
	}

	@Override
	public void createBindings(ReactiveBinder reactiveBinder) {
		checkBox.biBindSelected("done",reactiveBinder);
		nameLabel.bindText("name",reactiveBinder);
		descriptionEditor.biBindText("description",reactiveBinder);
		checkBox.addActionListener(reactiveBinder::react);
		removeButton.addActionListener((e)->onRemove.fire(getModel().getID()));
	}

	@Reactive("done")
	public void crossOut(boolean checked){
		if(normal == null)
			normal = nameLabel.getFont();
		if(striked == null) {
			Map attributes = normal.getAttributes();
			attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
			striked = new Font(attributes);
		}
		nameLabel.setFont(checked ? striked : normal);
		nameLabel.repaint();
	}

}
