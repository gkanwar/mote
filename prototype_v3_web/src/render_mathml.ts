// Render internal representations to MathML

import {OpFlavor, Expression, NumberExpr, SymbolExpr, AddOp, MulOp, SumOp, IntOp, InfOp} from './expression.js';
import {MathSymbol} from './symbols.js';

const PLUS_MINUS_SIGN = '&#xB1;';
const INVIS_TIMES = '&#x2062;';
const VIS_TIMES = '&#x00D7;';
const CIRC_TIMES = '&#x2297;';
const INT_SIGN = '&#x222B;';
const SUM_SIGN = '&#x2211;';
const MATH_INF_D = '&#x1d451;';
const PARTIAL_DIFF = '&#x2202;';

const SYMBOL_ID_CLASS = 'symbol-id';
const SYMBOL_SUB_ID_CLASS = 'symbol-sub-id';

function parenthesize(str: string): string {
    return '<mo>(</mo>' + str + '<mo>)</mo>';
}

export function renderSymbol(symbol: MathSymbol): string {
    return "<msub>" +
        `<mi class="${SYMBOL_ID_CLASS}">${symbol.name}</mi>` +
        `<mi class="${SYMBOL_SUB_ID_CLASS}">${symbol.subName}</mi>` +
        "</msub>";
}

export function renderExpr(expr: Expression, glueFlavor?: OpFlavor): string {
    switch (expr.kind) {
        case 'number':
            return `<mn>${(expr as NumberExpr).val}</mn>`;
        case 'symbol':
            return renderSymbol((expr as SymbolExpr).symbol);
        case 'add':
            let addStr = (expr as AddOp).args.map(
                expr => renderExpr(expr, OpFlavor.Add)
            ).join('<mo>+</mo>');
            if (glueFlavor === OpFlavor.Mul) {
                addStr = parenthesize(addStr);
            }
            return addStr;
        case 'mul':
            return (expr as MulOp).args.map(
                expr => renderExpr(expr, OpFlavor.Mul)
            ).join(`<mo>${INVIS_TIMES}</mo>`);
        case 'summation':
            // todo: limits, etc
            return `<mo>${SUM_SIGN}</mo><mrow>`+renderExpr((expr as SumOp).operand)+'</mrow>';
        case 'integration':
            return `<mo>${INT_SIGN}</mo><mrow>`+renderExpr((expr as IntOp).operand)+'</mrow>';
        case 'infinitesimal':
            return `<mrow>${MATH_INF_D}`+renderExpr((expr as InfOp).operand)+'</mrow>';
        default:
            const _exCheck: never = expr;
            return _exCheck;
    }
}
