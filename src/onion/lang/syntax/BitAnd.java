/* ************************************************************** *
 *                                                                *
 * Copyright (c) 2005, Kota Mizushima, All rights reserved.       *
 *                                                                *
 *                                                                *
 * This software is distributed under the modified BSD License.   *
 * ************************************************************** */
package onion.lang.syntax;

import onion.lang.syntax.visitor.ASTVisitor;

/**
 * @author Kota Mizushima
 *  
 */
public class BitAnd extends BinaryExpression {

  public BitAnd(final Expression left, final Expression right) {
    super("&", left, right);
  }

  public Object accept(final ASTVisitor visitor, final Object context) {
    return visitor.visit(this, context);
  }

}