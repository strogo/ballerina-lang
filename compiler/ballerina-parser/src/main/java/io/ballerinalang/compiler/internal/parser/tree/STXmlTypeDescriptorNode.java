/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package io.ballerinalang.compiler.internal.parser.tree;

import io.ballerinalang.compiler.syntax.tree.Node;
import io.ballerinalang.compiler.syntax.tree.NonTerminalNode;
import io.ballerinalang.compiler.syntax.tree.SyntaxKind;
import io.ballerinalang.compiler.syntax.tree.XmlTypeDescriptorNode;

import java.util.Collection;
import java.util.Collections;

/**
 * This is a generated internal syntax tree node.
 *
 * @since 2.0.0
 */
public class STXmlTypeDescriptorNode extends STTypeDescriptorNode {
    public final STNode xmlKeywordToken;
    public final STNode xmlTypeParamsNode;

    STXmlTypeDescriptorNode(
            STNode xmlKeywordToken,
            STNode xmlTypeParamsNode) {
        this(
                xmlKeywordToken,
                xmlTypeParamsNode,
                Collections.emptyList());
    }

    STXmlTypeDescriptorNode(
            STNode xmlKeywordToken,
            STNode xmlTypeParamsNode,
            Collection<STNodeDiagnostic> diagnostics) {
        super(SyntaxKind.XML_TYPE_DESC, diagnostics);
        this.xmlKeywordToken = xmlKeywordToken;
        this.xmlTypeParamsNode = xmlTypeParamsNode;

        addChildren(
                xmlKeywordToken,
                xmlTypeParamsNode);
    }

    public STNode modifyWith(Collection<STNodeDiagnostic> diagnostics) {
        return new STXmlTypeDescriptorNode(
                this.xmlKeywordToken,
                this.xmlTypeParamsNode,
                diagnostics);
    }

    public STXmlTypeDescriptorNode modify(
            STNode xmlKeywordToken,
            STNode xmlTypeParamsNode) {
        if (checkForReferenceEquality(
                xmlKeywordToken,
                xmlTypeParamsNode)) {
            return this;
        }

        return new STXmlTypeDescriptorNode(
                xmlKeywordToken,
                xmlTypeParamsNode,
                diagnostics);
    }

    public Node createFacade(int position, NonTerminalNode parent) {
        return new XmlTypeDescriptorNode(this, position, parent);
    }

    @Override
    public void accept(STNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T apply(STNodeTransformer<T> transformer) {
        return transformer.transform(this);
    }
}
