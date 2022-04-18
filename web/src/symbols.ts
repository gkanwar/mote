export class MathSymbol {
    name: string;
    subName: string;
    static symNameCounts: Map<string, number> = new Map();
    constructor(name: string, subName?: string) {
        this.name = name;
        if (subName == null) {
            if (!MathSymbol.symNameCounts.has(name)) {
                MathSymbol.symNameCounts.set(name, 0);
            }
            const count = MathSymbol.symNameCounts.get(name)!;
            subName = `#${count+1}`;
            MathSymbol.symNameCounts.set(name, count+1);
        }
        this.subName = subName;
    }
}
export type SymbolAction = {
    add?: MathSymbol,
    symTab: MathSymbol[]
};
type SymbolListener = (e: SymbolAction) => void;
var symTab: MathSymbol[] = [];
var symListeners: SymbolListener[] = [];

export function addSymbol(symbol: MathSymbol) {
    symTab.push(symbol);
    symListeners.forEach(f => f({
        add: symbol,
        symTab
    }));
}

export function addSymbolsListener(f: SymbolListener) {
    symListeners.push(f);
}

export function getSymbols() {
    return symTab;
}
