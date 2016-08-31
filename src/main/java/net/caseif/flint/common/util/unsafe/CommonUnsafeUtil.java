/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2016, Max Roncace <me@caseif.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.caseif.flint.common.util.unsafe;

import net.caseif.flint.util.unsafe.UnsafeUtil;

import java.util.regex.Pattern;

public abstract class CommonUnsafeUtil extends UnsafeUtil {

    private static final Pattern FLINT_PACKAGE_PATTERN = Pattern.compile("^net\\.caseif\\.flint");
    private static final Pattern UNSAFE_PACKAGE_PATTERN = Pattern.compile("^net\\.caseif\\.flint(.*)\\.util\\.unsafe");

    protected static void testInternalUse() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stack.length; i++) {
            if (!UNSAFE_PACKAGE_PATTERN.matcher(stack[i].getClassName()).find()) {
                if (FLINT_PACKAGE_PATTERN.matcher(stack[i].getClassName()).find()) {
                    break;
                } else {
                    throw new IllegalStateException("UnsafeUtil may not be used externally");
                }
            }
        }
    }

}
