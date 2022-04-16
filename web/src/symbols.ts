export class MathSymbol {
    name: string;
    subName: string;
    static symNameCounts = {};
    constructor(name: string, subName: string | null) {
        this.name = name;
        if (subName == null) {
            if (!MathSymbol.symNameCounts.hasOwnProperty(name)) {
                MathSymbol.symNameCounts[name] = 0;
            }
            const count = ++MathSymbol.symNameCounts[name];
            subName = `#${count}`;
        }
        this.subName = subName;
    }
}
var symTab: MathSymbol[] = [];
var symListeners = [];

export function addSymbol(symbol: MathSymbol) {
    symTab.push(symbol);
    symListeners.forEach(f => f({
        add: symbol,
        symTab
    }));
}

export function addSymbolsListener(f) {
    symListeners.push(f);
}

export function getSymbols() {
    return symTab;
}
