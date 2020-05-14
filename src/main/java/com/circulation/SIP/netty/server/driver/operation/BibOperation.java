/* 
 * Copyright (C) 2020 Ceridwen Limited
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.circulation.SIP.netty.server.driver.operation;

import com.circulation.SIP.messages.Bib;
import com.circulation.SIP.messages.BibResponse;
import com.circulation.SIP.messages.Recall;
import com.circulation.SIP.messages.RecallResponse;

public interface BibOperation {

	public abstract BibResponse Bib(Bib msg);

}