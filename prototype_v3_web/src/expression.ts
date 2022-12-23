// Representations of a full expression out of symbols and math operations

import {MathSymbol} from './symbols.js';

/// Simple numeric literal / symbol expressions
export interface NumberExpr {
    kind: 'number';
    val: number;
}
export function make_number_expr(val: number): NumberExpr {
    return {kind: 'number', val};
}
export interface SymbolExpr {
    kind: 'symbol';
    symbol: MathSymbol;
}
export function make_symbol_expr(symbol: MathSymbol): SymbolExpr {
    return {kind: 'symbol', symbol};
}

/// Operations
// Determines how the op gets rendered, parenthesized, etc
export enum OpFlavor {
    Mul, Add
}

interface ListOp {
    args: Expression[];
}

export interface AddOp extends ListOp {
    kind: 'add';
    opFlavor: OpFlavor.Add;
}
export function make_add_op(args: Expression[]): AddOp {
    return {kind: 'add', opFlavor: OpFlavor.Add, args};
}
export interface MulOp extends ListOp {
    kind: 'mul';
    opFlavor: OpFlavor.Mul;
}
export function make_mul_op(args: Expression[]): MulOp {
    return {kind: 'mul', opFlavor: OpFlavor.Mul, args};
}

interface OneOperandOp {
    operand: Expression;
}

export interface SumOp extends OneOperandOp {
    lowerLimit: Expression | null;
    upperLimit: Expression | null;
    sumVar: MathSymbol | null;
    kind: 'summation';
    opFlavor: OpFlavor.Add;
}
export function make_sum_op(
    operand: Expression, lowerLimit: Expression | null,
    upperLimit: Expression | null, sumVar: MathSymbol | null): SumOp {
    return {
        kind: 'summation',
        opFlavor: OpFlavor.Add,
        operand,
        lowerLimit,
        upperLimit,
        sumVar
    };
}

export interface IntOp extends OneOperandOp {
    lowerLimit: Expression | null;
    upperLimit: Expression | null;
    kind: 'integration';
    opFlavor: OpFlavor.Add;
}
export function make_int_op(
    operand: Expression, lowerLimit: Expression | null,
    upperLimit: Expression | null): IntOp {
    return {
        kind: 'integration',
        opFlavor: OpFlavor.Add,
        operand,
        lowerLimit,
        upperLimit
    };
}

export interface InfOp extends OneOperandOp {
    kind: 'infinitesimal';
    opFlavor: OpFlavor.Mul;
}
export function make_inf_op(operand: Expression): InfOp {
    return {operand, kind: 'infinitesimal', opFlavor: OpFlavor.Mul};
}


/// Expression is the appropriate union type
export type Expression = NumberExpr | SymbolExpr | AddOp | MulOp | SumOp | IntOp | InfOp;
