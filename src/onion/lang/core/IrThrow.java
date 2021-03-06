/* ************************************************************** *
 *                                                                *
 * Copyright (c) 2005, Kota Mizushima, All rights reserved.       *
 *                                                                *
 *                                                                *
 * This software is distributed under the modified BSD License.   *
 * ************************************************************** */
package onion.lang.core;

/**
 * @author Kota Mizushima
 * Date: 2005/06/17
 */
public class IrThrow implements IrStatement {
  public IrExpression expression;
  public IrThrow(IrExpression expression){
    this.expression = expression;
  }
}
