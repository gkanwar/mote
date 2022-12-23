import {MathSymbol, addSymbol, addSymbolsListener, getSymbols, SymbolAction} from './symbols.js';
import {renderSymbol, renderExpr} from './render_mathml.js';
import {make_number_expr, make_symbol_expr, make_add_op, make_mul_op, make_sum_op, make_int_op, make_inf_op} from './expression.js';



function createSymbol(name: string) {
    console.log(`Creating symbol ${name}`);
    addSymbol(new MathSymbol(name));
}

function makeSymbolElt(symbol: MathSymbol) {
    const elt = document.createElement('div');
    elt.setAttribute('class', 'symbol');
    elt.innerHTML = `<math><mrow>${renderSymbol(symbol)}</mrow></math>`;
    return elt;
}

// https://github.com/microsoft/TypeScript/issues/48267
type HTMLDialogElementHACK = HTMLDialogElement & {
    returnValue: string,
    showModal: () => void
};

function setupSymbolsFrame() {
    const symbolsFrame = document.getElementById('symbols')!;

    const createSymbolDialog = document.getElementById('createSymbolDialog') as HTMLDialogElementHACK;
    
    createSymbolDialog.addEventListener('close', function(event) {
        if (createSymbolDialog.returnValue !== 'create') return;
        const form = createSymbolDialog.querySelector('form')!;
        const inp = <HTMLInputElement>form.querySelector('[name=symbolName]')!;
        createSymbol(inp.value);
        form.reset();
    });

    const symbolsList = document.createElement('div');
    symbolsList.setAttribute('id', 'symbolsList');
    const addSymbolToList = function(symbol: MathSymbol) {
        symbolsList.appendChild(makeSymbolElt(symbol));
    };
    getSymbols().forEach(addSymbolToList);
    addSymbolsListener(function(event: SymbolAction) {
        if (event.add != null) {
            addSymbolToList(event.add);
        }
    });
    symbolsFrame.appendChild(symbolsList);
 
    const button = document.createElement('button');
    button.setAttribute('type', 'button');
    button.textContent = 'Create symbol';
    button.addEventListener('click', function() {
        createSymbolDialog.showModal();
    });
    symbolsFrame.appendChild(button);
}

function setupMainFrame() {
    const mainFrame = document.getElementById('main')!;

    const exprDiv = document.createElement('div');
    exprDiv.setAttribute('id', 'main-expr');
    exprDiv.innerHTML = '<math>' + renderExpr(make_int_op(
        make_mul_op([
            make_add_op([
                make_number_expr(1),
                make_symbol_expr(getSymbols()[0])
            ]),
            make_inf_op(make_symbol_expr(getSymbols()[0]))
        ]),
        null, null
    )) + '</math>';

    mainFrame.appendChild(exprDiv);
}

function init() {
    addSymbol(new MathSymbol('x'));
    addSymbol(new MathSymbol('y'));
    addSymbol(new MathSymbol('asdf'));
    addSymbol(new MathSymbol('z'));
    addSymbol(new MathSymbol('x'));
    setupSymbolsFrame();
    setupMainFrame();
    // const elt = document.getElementById('main');
    // if (elt) {
    //     elt.innerText = 'Hello, world???****?!';
    // }
}

window.addEventListener('DOMContentLoaded',  function(e) {
    init();
});

export {};
