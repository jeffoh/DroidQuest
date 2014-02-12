package com.droidquest.operation.swing.avatar;

import javax.swing.*;
import java.awt.FileDialog;

import com.droidquest.devices.SmallChip;
import com.droidquest.items.Item;
import com.droidquest.operation.Operation;
import com.droidquest.view.View;

/**
 * Operation that saves the state of a chip.
 */
public class SwingSaveChipOperation implements Operation {
    private final View view;
    private final Item currentAvatar;

    public SwingSaveChipOperation(Item currentAvatar, View view) {
        this.currentAvatar = currentAvatar;
        this.view = view;
    }

    @Override
    public boolean canExecute() {
        Item carried = currentAvatar.getCarrying();

        return carried instanceof SmallChip;
    }

    @Override
    public void execute() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(getViewComponent());
        FileDialog fd = new FileDialog(parentFrame,"Save Chip", FileDialog.SAVE);
        fd.setDirectory("chips");
        fd.setVisible(true);

        System.out.println("Dialog returned with "
                + fd.getDirectory()
                + fd.getFile());
        if (fd.getFile() != null)
            ((SmallChip) currentAvatar.getCarrying()).SaveChip(fd.getDirectory()+fd.getFile());
    }

    public JComponent getViewComponent() {
        return (JComponent) view;
    }
}