/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const s = require('string-plus');

import {MithrilComponent} from "jsx/mithril-component";
import * as m from "mithril";
import {Stream} from "mithril/stream";
import * as stream from "mithril/stream";
import * as style from "./index.scss";

interface Attrs {
  rewardText: string;
  key: string;
  query: string;
}

const STORAGE_KEY_PREFIX = "gocd.reward";

export class RewardBanner extends MithrilComponent<Attrs> {
  show: Stream<boolean> = stream(false);

  oninit(vnode: m.Vnode<Attrs>) {
    const storageKey = STORAGE_KEY_PREFIX.concat(".", vnode.attrs.key);
    if (!localStorage.getItem(storageKey) && !s.isBlank(vnode.attrs.query)) {
      this.show = stream(true);
      localStorage.setItem(storageKey, "true");
    }
  }

  view(vnode: m.Vnode<Attrs>) {
    if (this.show()) {
      return (
        <div class={style.rewardBanner} onclick={this.onClick.bind(this)}>
          🚀🚀🚀🚀
          {vnode.attrs.rewardText}&nbsp;
          🚀🚀🚀🚀
        </div>
      );
    }
  }

  oncreate(vnode: m.VnodeDOM<Attrs>) {
    function keyListener(e: KeyboardEvent) {
      // 27 is the code for the ESC key
      if (27 === e.which) {
        vnode.dom.remove();
      }
      document.body.removeEventListener("keydown", keyListener);
    }

    if (this.show()) {
      document.body.addEventListener("keydown", keyListener);
    }
  }

  onClick(event: MouseEvent): void {
    event.stopPropagation();
    event.toElement.remove();
  }
}
