import {InjectionKey} from 'vue'
import {createStore, useStore as baseUseStore, Store} from 'vuex'
export interface State {
    count: number
}
export const key: InjectionKey<Store<State>> = Symbol();

export const store = createStore<State>({
    state: {
        count: 0
    }
});
// 定义自己的组合函数
export function useStore() {
    return baseUseStore(key);
}
