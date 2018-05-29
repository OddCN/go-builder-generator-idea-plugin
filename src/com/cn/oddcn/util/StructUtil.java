package com.cn.oddcn.util;

import com.cn.oddcn.entity.StructEntity;
import com.cn.oddcn.entity.StructGenerateResult;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StructUtil {

    public static StructGenerateResult generateStruct(String selectedText) {
        if (StringUtils.isBlank(selectedText)) {
            return new StructGenerateResult("The text selected is blank", null);
        }

        String[] lines = selectedText.split(System.getProperty("line.separator"));

        List<StructEntity> structEntityList = new ArrayList<>();

        // match content
        String contentRegex = "^\\s*//";
        Pattern contentPattern = Pattern.compile(contentRegex);

        // match "type SomeStruct struct {"
        String structRegex = "^\\s*type\\s+(\\w+)\\s+struct\\s*\\{\\s*";
        Pattern structPattern = Pattern.compile(structRegex);

        // match struct key
        String keyRegex = "^\\s*(\\w+)\\s+([^.\\s]+)";
        Pattern keyPattern = Pattern.compile(keyRegex);

        // match end of struct
        String endRegex = "^\\s*}\\s*$";
        Pattern endPattern = Pattern.compile(endRegex);

        String str;
        for (int i = 0; i < lines.length; i++) {
            str = lines[i];

            if (contentPattern.matcher(str).find()) {
                continue;
            }

            Matcher structMatcher = structPattern.matcher(str);
            if (structMatcher.find()) {
                String structName = structMatcher.group(1);
                StructEntity structEntity = new StructEntity(structName);

                i++;
                for (; i < lines.length; i++) {
                    str = lines[i];

                    if (contentPattern.matcher(str).find()) {
                        continue;
                    }

                    Matcher keyMatcher = keyPattern.matcher(str);
                    if (keyMatcher.find()) {
                        structEntity.structKeyValue.put(keyMatcher.group(1), keyMatcher.group(2));
                    }

                    if (endPattern.matcher(str).find()) {
                        structEntityList.add(structEntity);
                        break;
                    }
                }
            }
        }
        if (structEntityList.isEmpty()) {
            return new StructGenerateResult("No complete struct in the selected text", null);
        }
        return new StructGenerateResult("", structEntityList);
    }
}
