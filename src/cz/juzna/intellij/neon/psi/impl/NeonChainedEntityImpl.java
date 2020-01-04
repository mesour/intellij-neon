package cz.juzna.intellij.neon.psi.impl;

import com.intellij.lang.ASTNode;
import cz.juzna.intellij.neon.parser.NeonElementTypes;
import cz.juzna.intellij.neon.psi.NeonChainedEntity;
import cz.juzna.intellij.neon.psi.NeonEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class NeonChainedEntityImpl extends NeonPsiElementImpl implements NeonChainedEntity {

	public NeonChainedEntityImpl(@NotNull ASTNode astNode) {
		super(astNode);
	}

	@Override
	public String toString() {
		return "Neon chained entity";
	}

	@Override
	public List<NeonEntity> getValues() {
		return findChildrenByType(NeonElementTypes.ENTITY);
	}

}
