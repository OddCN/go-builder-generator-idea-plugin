package com.cn.oddcn;

import com.cn.oddcn.entity.StructGenerateResult;
import com.cn.oddcn.util.BuilderUtil;
import com.cn.oddcn.util.StructUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang.StringUtils;

public class GoBuilderGeneratorAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);

        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (null == editor) {
            return;
        }

        SelectionModel selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();

        StructGenerateResult structGenerateResult = StructUtil.generateStruct(selectedText);
        if (!StringUtils.isBlank(structGenerateResult.error)) {
            Messages.showErrorDialog(project, structGenerateResult.error, "Generate Failed");
            return;
        }

        System.out.println(BuilderUtil.generateBuilderPatternCode(structGenerateResult.structEntityList));
    }
}
