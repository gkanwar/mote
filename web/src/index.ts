import {MathSymbol, addSymbol, addSymbolsListener, getSymbols} from './symbols.js';



function createSymbol(name: string) {
    console.log(`Creating symbol ${name}`);
    addSymbol(new MathSymbol(name));
}

function makeSymbolElt(symbol: MathSymbol) {
    const elt = document.createElement('div');
    elt.setAttribute('class', 'symbol');
    elt.innerHTML = `<math><mrow><msub><mi class="symbol-id">${symbol.name}</mi><mi class="symbol-sub-id">${symbol.subName}</mi></msub></mrow></math>`;
    return elt;
}

function setupSymbolsFrame() {
    const symbolsFrame = document.getElementById('symbols');

    const createSymbolDialog = document.getElementById('createSymbolDialog');
    createSymbolDialog.addEventListener('close', function(event) {
        if (createSymbolDialog.returnValue !== 'create') return;
        const form = createSymbolDialog.querySelector('form');
        createSymbol(form.querySelector('[name=symbolName]').value);
        form.reset();
    });

    const symbolsList = document.createElement('div');
    symbolsList.setAttribute('id', 'symbolsList');
    const addSymbolToList = function(symbol) {
        symbolsList.appendChild(makeSymbolElt(symbol));
    };
    getSymbols().forEach(addSymbolToList);
    addSymbolsListener(function(event) {
        if (event.hasOwnProperty('add')) {
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

function init() {
    addSymbol(new MathSymbol('x'));
    addSymbol(new MathSymbol('y'));
    addSymbol(new MathSymbol('asdf'));
    addSymbol(new MathSymbol('z'));
    addSymbol(new MathSymbol('x'));
    setupSymbolsFrame();
    // const elt = document.getElementById('main');
    // if (elt) {
    //     elt.innerText = 'Hello, world???****?!';
    // }
}

window.addEventListener('DOMContentLoaded',  function(e) {
    init();
});

export {};
