package com.cn.oddcn;

import com.cn.oddcn.entity.StructGenerateResult;
import com.cn.oddcn.util.BuilderUtil;
import com.cn.oddcn.util.StructUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class GoBuilderGeneratorAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);

        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (null == editor) {
            return;
        }

        String selectedText = editor.getSelectionModel().getSelectedText();

        StructGenerateResult structGenerateResult = StructUtil.generateStruct(selectedText);
        if (!StringUtils.isBlank(structGenerateResult.error)) {
            Messages.showErrorDialog(project, structGenerateResult.error, "Generate Failed");
            return;
        }

        String builderPatternCode = BuilderUtil.generateBuilderPatternCode(structGenerateResult.structEntityList);

        int textLength = editor.getDocument().getTextLength();

        new WriteCommandAction(project) {
            @Override
            protected void run(@NotNull Result result) {
                editor.getDocument().insertString(textLength, builderPatternCode);
                editor.getCaretModel().moveToOffset(textLength + 2);
                editor.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
            }
        }.execute();
    }
}
