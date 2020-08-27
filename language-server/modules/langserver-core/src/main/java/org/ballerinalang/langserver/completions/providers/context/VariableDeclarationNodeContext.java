/*
 * Copyright (c) 2020, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ballerinalang.langserver.completions.providers.context;

import io.ballerina.tools.text.TextRange;
import io.ballerinalang.compiler.syntax.tree.VariableDeclarationNode;
import org.ballerinalang.annotation.JavaSPIService;
import org.ballerinalang.langserver.commons.LSContext;
import org.ballerinalang.langserver.commons.completion.CompletionKeys;
import org.ballerinalang.langserver.commons.completion.LSCompletionException;
import org.ballerinalang.langserver.commons.completion.LSCompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Completion provider for {@link VariableDeclarationNode} context.
 *
 * @since 2.0.0
 */
@JavaSPIService("org.ballerinalang.langserver.commons.completion.spi.CompletionProvider")
public class VariableDeclarationNodeContext extends VariableDeclarationProvider<VariableDeclarationNode> {

    public VariableDeclarationNodeContext() {
        super(VariableDeclarationNode.class);
    }

    @Override
    public List<LSCompletionItem> getCompletions(LSContext context, VariableDeclarationNode node)
            throws LSCompletionException {
        if (!node.initializer().isPresent()) {
            return new ArrayList<>();
        }

        return this.initializerContextCompletions(context, node.typedBindingPattern().typeDescriptor(),
                node.initializer().get());
    }

    @Override
    public boolean onPreValidation(LSContext context, VariableDeclarationNode node) {
        if (!node.equalsToken().isPresent()) {
            return false;
        }
        Integer textPosition = context.get(CompletionKeys.TEXT_POSITION_IN_TREE);
        TextRange equalTokenRange = node.equalsToken().get().textRange();
        return equalTokenRange.endOffset() <= textPosition;
    }
}