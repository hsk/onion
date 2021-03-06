package onion.compiler.syntax

object AST {
  object Modifiers {
    final val INTERNAL = 1
    final val SYNCHRONIZED = 2
    final val FINAL = 4
    final val ABSTRACT = 8
    final val VOLATILE = 16
    final val STATIC = 32
    final val INHERITED = 64
    final val PUBLIC = 128
    final val PROTECTED = 256
    final val PRIVATE = 512
    final val FORWARDED = 1024
    def hasModifier(bitFlags: Int, modifier: Int): Boolean = (bitFlags & modifier) != 0      
  }
  abstract sealed class TypeDescriptor
  case class PrimitiveType(kind: PrimitiveTypeKind) extends TypeDescriptor
  case class ReferenceType(name: String, qualified: Boolean) extends TypeDescriptor
  case class ParameterizedType(component: TypeDescriptor, params: List[TypeDescriptor]) extends TypeDescriptor
  case class ArrayType(component: TypeDescriptor) extends TypeDescriptor
  abstract sealed class PrimitiveTypeKind
  case object KByte extends PrimitiveTypeKind
  case object KShort extends PrimitiveTypeKind
  case object KInt extends PrimitiveTypeKind
  case object KLong extends PrimitiveTypeKind
  case object KChar extends PrimitiveTypeKind
  case object KFloat extends PrimitiveTypeKind
  case object KDouble extends PrimitiveTypeKind
  case object KBoolean extends PrimitiveTypeKind
  case object KVoid extends PrimitiveTypeKind
  case class TypeNode(pos: Position, desc: TypeDescriptor) extends Node
  case class Position(line: Int, column: Int)
  abstract sealed class Node{ def pos: Position }
  case class Argument(pos: Position, name: String, typeRef: TypeNode) extends Node
  case class CompilationUnit(
    pos: Position, sourceFile: Option[String], module: Option[ModuleDeclaration],
    imports: Option[ImportClause], toplevels: List[Toplevel]) extends Node
  case class ModuleDeclaration(pos: Position, name: String) extends Node
  case class ImportClause(pos: Position, mapping: List[(String, String)]) extends Node
  abstract sealed class Toplevel extends Node
  abstract sealed class Expression extends Node
  abstract sealed class BinaryExpression(symbol: String) extends Expression {
    def left: Expression
    def right: Expression
  }
  abstract sealed class UnaryExpression(symbol: String) extends Expression {
    def target: Expression
  }
  case class Addition(pos: Position, left: Expression, right: Expression) extends BinaryExpression("+")
  case class AdditionAssignment(pos: Position, left: Expression, right: Expression) extends BinaryExpression("+=")
  case class Assignment(pos: Position, left: Expression, right: Expression) extends BinaryExpression("=")
  case class BitAnd(pos: Position, left: Expression, right: Expression) extends BinaryExpression("&")
  case class BitOr(pos: Position, left: Expression, right: Expression) extends BinaryExpression("|")
  case class BooleanLiteral(pos: Position, value: Boolean) extends Expression
  case class Cast(pos: Position, src: Expression, to: TypeNode) extends Expression
  case class CharacterLiteral(pos: Position, value: Char) extends Expression
  case class ClosureExpression(pos: Position, typeRef: TypeNode, args: Argument, returns: TypeNode, body: BlockStatement) extends Expression
  case class CurrentInstance(pos: Position) extends Expression
  case class Division(pos: Position, left: Expression, right: Expression) extends BinaryExpression("/")
  case class DivisionAssignment(pos: Position, left: Expression, right: Expression) extends BinaryExpression("/=")
  case class DoubleLiteral(pos: Position, value: Double) extends Expression
  case class Elvis(pos: Position, left: Expression, right: Expression) extends BinaryExpression(":?")
  case class Equal(pos: Position, left: Expression, right: Expression) extends BinaryExpression("==")
  case class FloatLiteral(pos: Position, value: Float) extends Expression
  case class GreaterOrEqual(pos: Position, left: Expression, right: Expression) extends BinaryExpression(">=")
  case class GreaterThan(pos: Position, left: Expression, right: Expression) extends BinaryExpression(">")
  case class Id(pos: Position, name: String) extends Expression
  case class Indexing(pos: Position, left: Expression, right: Expression) extends BinaryExpression("[]")
  case class IntegerLiteral(pos: Position, value: Int) extends Expression
  case class IsInstance(pos: Position, target: Expression, typeRef: TypeNode) extends Expression
  case class LessOrEqual(pos: Position, left: Expression, right: Expression) extends BinaryExpression("<=")
  case class LessThan(pos: Position, left: Expression, right: Expression) extends BinaryExpression("<")  
  case class ListLiteral(pos: Position, elements: List[Expression]) extends Expression
  case class LogicalAnd(pos: Position, left: Expression, right: Expression) extends BinaryExpression("&&")
  case class LogicalOr(pos: Position, left: Expression, right: Expression) extends BinaryExpression("||")
  case class LogicalRightShift(pos: Position, left: Expression, right: Expression) extends BinaryExpression(">>>")
  case class LongLiteral(pos: Position, value: Long) extends Expression
  case class MathLeftShift(pos: Position, left: Expression, right: Expression) extends BinaryExpression("<<")
  case class MathRightShift(pos: Position, left: Expression, right: Expression) extends BinaryExpression(">>")
  case class MemberSelection(pos: Position, target: Expression, name: String) extends Expression
  case class MethodCall(pos: Position, target: Expression, name: String, args: List[Expression]) extends Expression
  case class Modulo(pos: Position, left: Expression, right: Expression) extends BinaryExpression("%")
  case class ModuloAssignment(pos: Position, left: Expression, right: Expression) extends BinaryExpression("%=")
  case class Multiplication(pos: Position, left: Expression, right: Expression) extends BinaryExpression("*")
  case class MultiplicationAssignment(pos: Position, left: Expression, right: Expression) extends BinaryExpression("*=")
  case class Negate(pos: Position, target: Expression) extends UnaryExpression("-")
  case class NewArray(pos: Position, typeRef: TypeNode, args: List[Expression]) extends Expression
  case class NewObject(pos: Position, typeRef: TypeNode, args: List[Expression]) extends Expression
  case class Not(pos: Position, target: Expression) extends UnaryExpression("!")
  case class NotEqual(pos: Position, left: Expression, right: Expression) extends BinaryExpression("!=")
  case class NullLiteral(pos: Position) extends Expression
  case class Posit(pos: Position, target: Expression) extends UnaryExpression("+")
  case class PostDecrement(pos: Position, target: Expression) extends UnaryExpression("--")
  case class PostIncrement(pos: Position, target: Expression) extends UnaryExpression("++")
  case class ReferenceEqual(pos: Position, left: Expression, right: Expression) extends BinaryExpression("===")
  case class ReferenceNotEqual(pos: Position, left: Expression, right: Expression) extends BinaryExpression("!==")
  case class UnqualifiedFieldReference(pos: Position, name: String) extends Expression
  case class UnqualifiedMethodCall(pos: Position, name: String, args: Expression) extends Expression
  case class StaticIDExpression(pos: Position, typeRef: TypeNode, name: String) extends Expression
  case class StaticMethodCall(pos: Position, typeRef: TypeNode, name: String, args: List[Expression]) extends Expression
  case class StringLiteral(pos: Position, value: String) extends Expression
  case class Subtraction(pos: Position, left: Expression, right: Expression) extends BinaryExpression("-")
  case class SubtractionAssignment(pos: Position, left: Expression, right: Expression) extends BinaryExpression("-=")
  case class SuperMethodCall(pos: Position, name: String, args: List[Expression]) extends Expression
  case class XOR(pos: Position, left: Expression, right: Expression) extends BinaryExpression("^")  
  
  abstract sealed class Statement extends Toplevel
  case class BlockStatement(pos: Position, elements: List[Statement]) extends Statement
  case class BreakStatement(pos: Position) extends Statement
  case class CondStatement(pos: Position, clauses: List[(Expression, BlockStatement)], elseBlock: Option[BlockStatement]) extends Statement
  case class ContinueStatement(pos: Position) extends Statement
  case class EmptyStatement(pos: Position) extends Statement
  case class ExpressionStatement(pos: Position, body: Expression) extends Statement
  case class ForeachStatement(pos: Position, arg: Argument, collection: Expression, statement: BlockStatement) extends Statement
  case class ForStatement(pos: Position, init: Option[Statement], condition: Option[Expression], update: Option[Expression], block: BlockStatement) extends Statement
  case class IfStatement(pos: Position, condition: Expression, thenBlock: BlockStatement, elseBlock: Option[BlockStatement]) extends Statement
  case class LocalVariableDeclaration(pos: Position, name: String, typeRef: Option[TypeNode], init: Expression) extends Statement
  case class ReturnStatement(pos: Position) extends Statement
  case class SelectStatement(pos: Position, condition: Expression, cases: List[(List[Expression], BlockStatement)], elseBlock: Option[BlockStatement]) extends Statement
  case class SynchronizedStatement(pos: Position, condition: Expression, block: BlockStatement) extends Statement
  case class ThrowStatement(pos: Position, target: Expression) extends Statement
  case class TryStatement(pos: Position, tryBlock: BlockStatement, recClauses: List[(Argument, BlockStatement)], finBlock: Option[BlockStatement]) extends Statement
  case class WhileStatement(pos: Position, condition: Expression, block: BlockStatement) extends Statement
  
  case class FunctionDeclaration(pos: Position, modifiers: Int, name: String, args: List[Argument], returnType: TypeNode, block: BlockStatement) extends Toplevel
  case class GlobalVariableDeclaration(pos: Position, modifiers: Int, name: String, typeRef: TypeNode, init: Option[Expression]) extends Toplevel
  
  abstract sealed class MemberDeclaration extends Node { def modifiers: Int; def name: String } 
  case class MethodDeclaration(pos: Position, modifiers: Int, name: String, args: List[Argument], returnType: TypeNode, block: BlockStatement) extends MemberDeclaration
  case class FieldDeclaration(pos: Position, modifiers: Int, name: String, typeRef: TypeNode, init: Option[Expression]) extends MemberDeclaration
  case class ConstructorDeclaration(pos: Position, modifiers: Int, args: List[Argument], superInits: List[Expression], block: BlockStatement) extends MemberDeclaration { val name = "new" }
  
  case class AccessSection(pos: Position, modifiers: Int, members: List[MemberDeclaration]) extends Node
  abstract sealed class TypeDeclaration extends Toplevel { def modifiers: Int; def name: String }
  case class ClassDeclaration(pos: Position, modifiers: Int, name: String, superClass: TypeNode, superInterfaces: List[TypeNode], defaultSection: AccessSection, sections: List[AccessSection]) extends TypeDeclaration
  case class InterfaceDeclaration(pos: Position, modifiers: Int, name: String, superInterfaces: List[TypeNode], methods: List[MethodDeclaration]) extends TypeDeclaration
}
