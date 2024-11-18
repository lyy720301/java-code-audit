## ReDos
ReDoS攻击是一种算法复杂性攻击，利用了字符串匹配正则表达式的最坏情况，由正则表达式回溯机制引起。

### 样例
```javascript
const regex = /(g|i+)+t/
console.time('t1:')
'giiiiiiiiiiiiiiiiiiiiiiv'.search(regex);
console.timeEnd('t1:')

console.time('t2:')
'giiiiiiiiiiiiiiiiiiiiiit'.search(regex);
console.timeEnd('t2:')
```
输出
```text
t1:: 866.334ms
t2:: 0.047ms
```

### 结果分析
以下分析结果来自ChatGPT

---

正则表达式 `/(g|i+)+t/` 中有以下几个特点，会导致回溯问题：
1. **嵌套的量词**：
    - `(g|i+)` 中的 `i+` 是一个量词，它允许匹配多个 `i`。
    - 整个捕获组 `(g|i+)` 外又有一个 `+`，这意味着捕获组本身可以被重复匹配。
    - 嵌套的量词会导致正则引擎尝试多种可能的分组方式（称为回溯），以满足后续匹配。

2. **无匹配结果时的回溯**：
    - 在第一个例子 `'giiiiiiiiiiiiiiiiiiiiiiv'` 中，正则引擎尝试匹配 `(g|i+)`，可以快速判断 `v` 不匹配 `t`，直接返回失败，回溯很少。
    - 在第二个例子 `'giiiiiiiiiiiiiiiiiiiiiit'` 中，虽然有 `t`，但在尝试 `(g|i+)+` 时，正则引擎会尝试**每一种可能的分组方式**，以确保匹配成功。
        - 例如，它可能尝试：
            - 将整个字符串匹配为 `g` + `i+` + `t`。
            - 将前半部分匹配为 `(g|i+)`，再逐步尝试不同长度的 `i` 等。

3. **“贪婪模式”带来的复杂性**：
    - 默认情况下，`+` 是贪婪量词（尽可能多地匹配字符）。
    - 因此，正则引擎会先尝试匹配尽可能长的序列（`i+`），如果后面不匹配 `t`，它会回溯到次长的序列，然后再试。
    - 当字符串非常长时，这种回溯的次数会指数级增长。

---

#### 为什么第一个快，第二个慢？
1. **第一个例子 (`'giiiiiiiiiiiiiiiiiiiiiiv'`)**：
    - 匹配 `giiiiiiiiiiiiiiiiiiiiiiv` 的时候，发现最后的 `v` 不是 `t`。
    - 正则引擎可以快速确定失败，不需要尝试大量回溯。

2. **第二个例子 (`'giiiiiiiiiiiiiiiiiiiiiit'`)**：
    - 匹配 `giiiiiiiiiiiiiiiiiiiiiit` 时，最后的 `t` 符合条件。
    - 正则引擎需要尝试所有可能的分组方式来验证 `(g|i+)+` 能否匹配。
    - 这种尝试会随着字符串长度的增长成指数级增长，导致性能问题。

---

#### 解决方法
1. **避免嵌套量词**：
   尽量减少这种容易触发回溯的正则结构。例如，改写正则为：
   ```javascript
   const regex = /gi+t/;
   ```
   这样限制了结构的复杂性，显著提升效率。

2. **使用懒惰量词**：
   可以将 `+` 替换为 `+?`，让正则尽量少匹配而不是尽量多匹配：
   ```javascript
   const regex = /(g|i+?)+t/;
   ```

3. **特定优化匹配逻辑**：
   如果输入是已知的字符结构，可以优化正则逻辑。例如：
   ```javascript
   const regex = /g(i+)*t/;
   ```
   直接移除不必要的复杂捕获组 `(g|i+)`。

4. **使用工具检测正则性能**：
   使用工具（如 [regex101](https://regex101.com/)）来分析回溯行为，帮助你理解问题并优化表达式。

---

#### 示例优化
```javascript
console.time('optimized')
'giiiiiiiiiiiiiiiiiiiiiit'.search(/gi+t/);
console.timeEnd('optimized')
```
这种优化消除了嵌套的捕获组，性能将大幅提升。


### 预防措施
- 对入参大小限制
- 使用能确保线性匹配时间的引擎