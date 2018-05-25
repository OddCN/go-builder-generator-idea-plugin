package com.cn.oddcn;

import com.cn.oddcn.util.BuilderUtil;
import com.cn.oddcn.util.StructUtil;
import com.cn.oddcn.entity.StructEntity;
import com.cn.oddcn.entity.StructGenerateResult;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class GoBuilderGeneratorAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (null == editor) {
            return;
        }

        SelectionModel selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        if (StringUtils.isBlank(selectedText)) {
            return;
        }

        System.out.println(selectedText);

        StructGenerateResult structGenerateResult = StructUtil.generateStruct(selectedText);
        for (StructEntity structEntity : structGenerateResult.structEntityList) {
            System.out.println(structEntity.structName);
            for (Map.Entry<String, String> entry : structEntity.structKeyValue.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        }

        System.out.println(BuilderUtil.generateBuilderPatternCode(structGenerateResult.structEntityList));
    }
}
