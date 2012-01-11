/*
 [The "BSD license"]
 Copyright (c) 2012 Terence Parr
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.antlr.v4.codegen.model;

import org.antlr.v4.codegen.CodeGenerator;
import org.antlr.v4.codegen.OutputModelFactory;
import org.antlr.v4.codegen.model.decl.RuleContextDecl;
import org.antlr.v4.tool.LabelElementPair;
import org.antlr.v4.tool.LeftRecursiveRule;
import org.antlr.v4.tool.Rule;
import org.stringtemplate.v4.misc.MultiMap;

import java.util.Iterator;
import java.util.Set;

public class LeftRecursiveRuleFunction extends RuleFunction {
	public LeftRecursiveRuleFunction(OutputModelFactory factory, LeftRecursiveRule r) {
		super(factory, r);

		// Since we delete x=lr, we have to manually add decls for all labels on left-recur refs
		CodeGenerator gen = factory.getGenerator();
		MultiMap<String,LabelElementPair> labelDefs = r.alt[1].labelDefs;
		Set<String> labels = labelDefs.keySet();
		for (Iterator<String> iterator = labels.iterator(); iterator.hasNext(); ) {
			String label = iterator.next();
			LabelElementPair l = r.getAnyLabelDef(label);
			Rule targetRule = factory.getGrammar().getRule(l.element.getText());
			String ctxName = gen.target.getRuleFunctionContextStructName(targetRule);
			RuleContextDecl d = new RuleContextDecl(factory,label,ctxName);
			addContextDecl(d);
		}
	}
}