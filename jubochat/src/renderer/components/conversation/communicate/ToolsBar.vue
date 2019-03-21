<template>
  <div class="toolbar">
    <!-- 表情窗口 -->
    <div class="expressions scroll" v-show="showExpression">
      <img class="qq-face" src="static/img/qqface.png" usemap="#qqMap">
      <map name="qqMap">
        <area
          v-for="(expression, index) in 105"
          :key="index"
          shape="rect"
          :coords="(index%15)*25 + ', ' + parseInt(index/15)*25 +', ' + (index%15+1)*25 + ', ' +(parseInt(index/15)+1)*25"
          :title="expressionMap[index]"
          @click="selectExpression(index)"
        >
      </map>
      <img src="static/img/emoji.png" @click="selectExpression()" usemap="#emojiMap">
      <map name="emojiMap">
        <area
          v-for="(emoji, index) in 177"
          :key="index"
          shape="rect"
          :coords="(index%15)*25 + ', ' + parseInt(index/15)*25 +', ' + (index%15+1)*25 + ', ' +(parseInt(index/15)+1)*25"
          :title="expressionMap[105+index]"
          @click="selectExpression(105+index)"
        >
      </map>
    </div>

    <!-- 选项 -->
    <div class="tool1 flex-around">
      <!-- 表情 -->
      <i class="fa fa-smile-o hover" aria-hidden="true" title="表情" @click="toggleExpression"></i>
      <!-- 发图片 -->
      <label for="fileInput">
        <i class="fa fa-picture-o" aria-hidden="true" title="发送图片"></i>
      </label>
      <input
        v-show="false"
        accept=".jpg, .png, .gif, .jpeg, .mp4"
        type="file"
        id="fileInput"
        ref="toolsmedia"
        @change="sendMedia"
      >
      <!-- 发文件 -->
      <label for="sendFiles">
        <i class="fa fa-folder-o" aria-hidden="true" title="发送文件"></i>
      </label>
      <input
        v-show="false"
        type="file"
        id="sendFiles"
        accept=".txt, .doc, .xls, .ppt, .docx, .xlsx, .pptx, .rar, .zip, .exe, .text, .apk, .mp3"
        ref="toolsFile"
        @change="sendFile"
      >
      <!-- 发名片 -->
      <i class="fa fa-address-card-o" aria-hidden="true" title="发名片"></i>
      <!-- 发红包 -->
      <icon-font class="icon" height="24" width="24">
        <tools-red/>
      </icon-font>
      <!-- 转账 -->
      <icon-font class="icon" height="24" width="24">
        <tools-trans/>
      </icon-font>
      <!-- 置顶 -->
      <!-- <i class="fa fa-upload no-info" title="置顶" aria-hidden="true"></i> -->
      <!-- 免打扰 -->
      <!-- <i class="fa fa-bell-slash-o no-info" title="免打扰" aria-hidden="true"></i> -->
      <!-- 删除 -->
      <!-- <i class="fa fa-trash no-info" title="删除" aria-hidden="true"></i> -->
    </div>
    <div class="tool2 flex-center">
      <!-- 朋友圈 -->
      <div class="pointer" @click="showFC" title="好友朋友圈">
        <icon-font height="24" width="24">
          <friend-circle/>
        </icon-font>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  data () {
    return {
      showExpression: false,
      toolsContent: {
        contentType: 0,
        content: '',
        name: '',
        size: 0,
        fileType: ''
      },
      expressionMap: {
        0: '微笑',
        1: '撇嘴',
        2: '色',
        3: '发呆',
        4: '得意',
        5: '流泪',
        6: '害羞',
        7: '闭嘴',
        8: '睡',
        9: '大哭',
        10: '尴尬',
        11: '发怒',
        12: '调皮',
        13: '呲牙',
        14: '惊讶',
        15: '难过',
        16: '酷',
        17: '冷汗',
        18: '抓狂',
        19: '吐',
        20: '偷笑',
        21: '愉快',
        22: '白眼',
        23: '傲慢',
        24: '饥饿',
        25: '困',
        26: '惊恐',
        27: '流汗',
        28: '憨笑',
        29: '悠闲',
        30: '奋斗',
        31: '咒骂',
        32: '疑问',
        33: '嘘',
        34: '晕',
        35: '疯了',
        36: '衰',
        37: '骷髅',
        38: '敲打',
        39: '再见',
        40: '擦汗',
        41: '抠鼻',
        42: '鼓掌',
        43: '糗大了',
        44: '坏笑',
        45: '左哼哼',
        46: '右哼哼',
        47: '哈欠',
        48: '鄙视',
        49: '委屈',
        50: '快哭了',
        51: '阴险',
        52: '亲亲',
        53: '吓',
        54: '可怜',
        55: '菜刀',
        56: '西瓜',
        57: '啤酒',
        58: '篮球',
        59: '乒乓',
        60: '咖啡',
        61: '饭',
        62: '猪头',
        63: '玫瑰',
        64: '凋谢',
        65: '嘴唇',
        66: '爱心',
        67: '心碎',
        68: '蛋糕',
        69: '闪电',
        70: '炸弹',
        71: '刀',
        72: '足球',
        73: '瓢虫',
        74: '便便',
        75: '月亮',
        76: '太阳',
        77: '礼物',
        78: '拥抱',
        79: '强',
        80: '弱',
        81: '握手',
        82: '胜利',
        83: '抱拳',
        84: '勾引',
        85: '拳头',
        86: '差劲',
        87: '爱你',
        88: 'NO',
        89: 'OK',
        90: '爱情',
        91: '飞吻',
        92: '跳跳',
        93: '发抖',
        94: '怄火',
        95: '转圈',
        96: '磕头',
        97: '回头',
        98: '跳绳',
        99: '投降',
        100: '激动',
        101: '乱舞',
        102: '献吻',
        103: '左太极',
        104: '右太极',
        105: '笑脸',
        106: '生病',
        107: '破涕为笑',
        108: '吐舌',
        109: '脸红',
        110: '恐惧',
        111: '失望',
        112: '无语',
        113: '嘿哈',
        114: '捂脸',
        115: '奸笑',
        116: '机智',
        117: '皱眉',
        118: '耶',
        119: '鬼魂',
        120: '合十',
        121: '强壮',
        122: '庆祝',
        123: '礼物',
        124: '红包',
        125: '鸡',
        126: '开心',
        127: '大笑',
        128: '热情',
        129: '眨眼',
        130: '色',
        131: '接吻',
        132: '亲吻',
        133: '露齿笑',
        134: '满意',
        135: '戏弄',
        136: '得意',
        137: '汗',
        138: '低落',
        139: '呸',
        140: '焦虑',
        141: '担心',
        142: '震惊',
        143: '悔恨',
        144: '眼泪',
        145: '哭',
        146: '晕',
        147: '心烦',
        148: '生气',
        149: '睡觉',
        150: '恶魔',
        151: '外星人',
        152: '心',
        153: '心碎',
        154: '丘比特',
        155: '闪烁',
        156: '星星',
        157: '叹号',
        158: '问号',
        159: '睡着',
        160: '水滴',
        161: '音乐',
        162: '火',
        163: '便便',
        164: '强',
        165: '弱',
        166: '拳头',
        167: '胜利',
        168: '上',
        169: '下',
        170: '右',
        171: '左',
        172: '第一',
        173: '吻',
        174: '热恋',
        175: '男孩',
        176: '女孩',
        177: '女士',
        178: '男士',
        179: '天使',
        180: '骷髅',
        181: '红唇',
        182: '太阳',
        183: '下雨',
        184: '多云',
        185: '雪人',
        186: '月亮',
        187: '闪电',
        188: '海浪',
        189: '猫',
        190: '小狗',
        191: '老鼠',
        192: '仓鼠',
        193: '兔子',
        194: '狗',
        195: '青蛙',
        196: '老虎',
        197: '考拉',
        198: '熊',
        199: '猪',
        200: '牛',
        201: '野猪',
        202: '猴子',
        203: '马',
        204: '蛇',
        205: '鸽子',
        206: '鸡',
        207: '企鹅',
        208: '毛虫',
        209: '章鱼',
        210: '鱼',
        211: '鲸鱼',
        212: '海豚',
        213: '玫瑰',
        214: '花',
        215: '棕榈树',
        216: '仙人掌',
        217: '礼盒',
        218: '南瓜灯',
        219: '圣诞老人',
        220: '圣诞树',
        221: '铃',
        222: '气球',
        223: 'CD',
        224: '相机',
        225: '录像机',
        226: '电脑',
        227: '电视',
        228: '电话',
        229: '解锁',
        230: '锁',
        231: '钥匙',
        232: '成交',
        233: '灯泡',
        234: '邮箱',
        235: '浴缸',
        236: '钱',
        237: '炸弹',
        238: '手枪',
        239: '药丸',
        240: '橄榄球',
        241: '篮球',
        242: '足球',
        243: '棒球',
        244: '高尔夫',
        245: '奖杯',
        246: '入侵者',
        247: '唱歌',
        248: '吉他',
        249: '比基尼',
        250: '皇冠',
        251: '雨伞',
        252: '手提包',
        253: '口红',
        254: '戒子',
        255: '砖石',
        256: '咖啡',
        257: '啤酒',
        258: '干杯',
        259: '鸡尾酒',
        260: '汉堡',
        261: '薯条',
        262: '意面',
        263: '寿司',
        264: '面条',
        265: '煎蛋',
        266: '冰激凌',
        267: '蛋糕',
        268: '苹果',
        269: '飞机',
        270: '火箭',
        271: '自行车',
        272: '高铁',
        273: '警告',
        274: '旗',
        275: '男人',
        276: '女人',
        277: 'O',
        278: 'X',
        279: '版权',
        280: '注册商标',
        281: '商标'
      },
      emojiMap: {
        '<笑脸>': '1f604',
        '<笑臉>': '1f604',
        '<Laugh>': '1f604',
        '<开心>': '1f60a',
        '<開心>': '1f60a',
        '<Happy>': '1f60a',
        '<大笑>': '1f603',
        '<Big Smile>': '1f603',
        '<热情>': '263a',
        '<熱情>': '263a',
        '<Glowing>': '263a',
        '<眨眼>': '1f609',
        '<Wink>': '1f609',
        '<色>': '1f60d',
        '<Love>': '1f60d',
        '<Drool>': '1f60d',
        '<接吻>': '1f618',
        '<Smooch>': '1f618',
        '<亲吻>': '1f61a',
        '<親吻>': '1f61a',
        '<Kiss>': '1f61a',
        '<脸红>': '1f633',
        '<臉紅>': '1f633',
        '<Blush>': '1f633',
        '<露齿笑>': '1f63c',
        '<露齒笑>': '1f63c',
        '<Grin>': '1f63c',
        '<满意>': '1f60c',
        '<滿意>': '1f60c',
        '<Satisfied>': '1f60c',
        '<戏弄>': '1f61c',
        '<戲弄>': '1f61c',
        '<Tease>': '1f61c',
        '<吐舌>': '1f445',
        '<Tongue>': '1f445',
        '<无语>': '1f612',
        '<無語>': '1f612',
        '<Speechless>': '1f612',
        '<得意>': '1f60f',
        '<Smirk>': '1f60f',
        '<CoolGuy>': '1f60f',
        '<汗>': '1f613',
        '<Sweat>': '1f613',
        '<失望>': '1f640',
        '<Let Down>': '1f640',
        '<合十>': '1f64f',
        '<祈禱>': '1f64f',
        '<低落>': '1f61e',
        '<Low>': '1f61e',
        '<呸>': '1f616',
        '<Ugh>': '1f616',
        '<焦虑>': '1f625',
        '<焦慮>': '1f625',
        '<Anxious>': '1f625',
        '<担心>': '1f630',
        '<擔心>': '1f630',
        '<Worried>': '1f630',
        '<震惊>': '1f628',
        '<震驚>': '1f628',
        '<Shocked>': '1f628',
        '<悔恨>': '1f62b',
        '<D’oh!>': '1f62b',
        '<眼泪>': '1f622',
        '<眼淚>': '1f622',
        '<Tear>': '1f622',
        '<哭>': '1f62d',
        '<Cry>': '1f62d',
        '<破涕为笑>': '1f602',
        '<破涕為笑>': '1f602',
        '<Lol>': '1f602',
        '<晕>': '1f632',
        '<Dead>': '1f632',
        '<Dizzy>': '1f632',
        '<恐惧>': '1f631',
        '<恐懼>': '1f631',
        '<Terror>': '1f631',
        '<心烦>': '1f620',
        '<心煩>': '1f620',
        '<Upset>': '1f620',
        '<生气>': '1f63e',
        '<生氣>': '1f63e',
        '<Angry>': '1f63e',
        '<睡觉>': '1f62a',
        '<睡覺>': '1f62a',
        '<Zzz>': '1f62a',
        '<生病>': '1f637',
        '<Sick>': '1f637',
        '<恶魔>': '1f47f',
        '<惡魔>': '1f47f',
        '<Demon>': '1f47f',
        '<外星人>': '1f47d',
        '<Alien>': '1f47d',
        '<心>': '2764',
        '<Heart>': '2764',
        '<心碎>': '1f494',
        '<Heartbroken>': '1f494',
        '<BrokenHeart>': '1f494',
        '<丘比特>': '1f498',
        '<Cupid>': '1f498',
        '<闪烁>': '2728',
        '<閃爍>': '2728',
        '<Twinkle>': '2728',
        '<星星>': '1f31f',
        '<Star>': '1f31f',
        '<叹号>': '2755',
        '<嘆號>': '2755',
        '<!>': '2755',
        '<问号>': '2754',
        '<問號>': '2754',
        '<?>': '2754',
        '<睡着>': '1f4a4',
        '<睡著>': '1f4a4',
        '<Asleep>': '1f4a4',
        '<水滴>': '1f4a6',
        '<Drops>': '1f4a6',
        '<音乐>': '1f3b5',
        '<音樂>': '1f3b5',
        '<Music>': '1f3b5',
        '<火>': '1f525',
        '<Fire>': '1f525',
        '<便便>': '1f4a9',
        '<Poop>': '1f4a9',
        '<强>': '1f44d',
        '<強>': '1f44d',
        '<ThumbsUp>': '1f44d',
        '<弱>': '1f44e',
        '<ThumbsDown>': '1f44e',
        '<拳头>': '1f44a',
        '<拳頭>': '1f44a',
        '<Punch>': '1f44a',
        '<Fist>': '1f44a',
        '<胜利>': '270c',
        '<勝利>': '270c',
        '<Peace>': '270c',
        '<上>': '1f446',
        '<Up>': '1f446',
        '<下>': '1f447',
        '<Down>': '1f447',
        '<右>': '1f449',
        '<Right>': '1f449',
        '<左>': '1f448',
        '<Left>': '1f448',
        '<第一>': '261d',
        '<#1>': '261d',
        '<强壮>': '1f4aa',
        '<強壯>': '1f4aa',
        '<Strong>': '1f4aa',
        '<吻>': '1f48f',
        '<Kissing>': '1f48f',
        '<热恋>': '1f491',
        '<熱戀>': '1f491',
        '<Couple>': '1f491',
        '<男孩>': '1f466',
        '<Boy>': '1f466',
        '<女孩>': '1f467',
        '<Girl>': '1f467',
        '<女士>': '1f469',
        '<Lady>': '1f469',
        '<男士>': '1f468',
        '<Man>': '1f468',
        '<天使>': '1f47c',
        '<Angel>': '1f47c',
        '<骷髅>': '1f480',
        '<骷髏頭>': '1f480',
        '<骷髏>': '1f480',
        '<Skull>': '1f480',
        '<红唇>': '1f48b',
        '<紅唇>': '1f48b',
        '<Lips>': '1f48b',
        '<太阳>': '2600',
        '<太陽>': '2600',
        '<Sun>': '2600',
        '<下雨>': '2614',
        '<Rain>': '2614',
        '<多云>': '2601',
        '<多雲>': '2601',
        '<Cloud>': '2601',
        '<雪人>': '26c4',
        '<Snowman>': '26c4',
        '<月亮>': '1f319',
        '<Moon>': '1f319',
        '<闪电>': '26a1',
        '<閃電>': '26a1',
        '<Lightning>': '26a1',
        '<海浪>': '1f30a',
        '<Waves>': '1f30a',
        '<猫>': '1f431',
        '<貓>': '1f431',
        '<Cat>': '1f431',
        '<小狗>': '1f429',
        '<Doggy>': '1f429',
        '<老鼠>': '1f42d',
        '<Mouse>': '1f42d',
        '<仓鼠>': '1f439',
        '<倉鼠>': '1f439',
        '<Hamster>': '1f439',
        '<兔子>': '1f430',
        '<Rabbit>': '1f430',
        '<狗>': '1f43a',
        '<Dog>': '1f43a',
        '<青蛙>': '1f438',
        '<Frog>': '1f438',
        '<老虎>': '1f42f',
        '<Tiger>': '1f42f',
        '<考拉>': '1f428',
        '<Koala>': '1f428',
        '<熊>': '1f43b',
        '<Bear>': '1f43b',
        '<猪>': '1f437',
        '<豬>': '1f437',
        '<Pig>': '1f437',
        '<牛>': '1f42e',
        '<Cow>': '1f42e',
        '<野猪>': '1f417',
        '<野豬>': '1f417',
        '<Boar>': '1f417',
        '<猴子>': '1f435',
        '<Monkey>': '1f435',
        '<马>': '1f434',
        '<馬>': '1f434',
        '<Horse>': '1f434',
        '<蛇>': '1f40d',
        '<Snake>': '1f40d',
        '<鸽子>': '1f426',
        '<鴿子>': '1f426',
        '<Pigeon>': '1f426',
        '<鸡>': '1f414',
        '<雞>': '1f414',
        '<Chicken>': '1f414',
        '<企鹅>': '1f427',
        '<企鵝>': '1f427',
        '<Penguin>': '1f427',
        '<毛虫>': '1f41b',
        '<毛蟲>': '1f41b',
        '<Caterpillar>': '1f41b',
        '<章鱼>': '1f419',
        '<八爪魚>': '1f419',
        '<Octopus>': '1f419',
        '<鱼>': '1f420',
        '<魚>': '1f420',
        '<Fish>': '1f420',
        '<鲸鱼>': '1f433',
        '<鯨魚>': '1f433',
        '<Whale>': '1f433',
        '<海豚>': '1f42c',
        '<Dolphin>': '1f42c',
        '<玫瑰>': '1f339',
        '<Rose>': '1f339',
        '<花>': '1f33a',
        '<Flower>': '1f33a',
        '<棕榈树>': '1f334',
        '<棕櫚樹>': '1f334',
        '<Palm>': '1f334',
        '<仙人掌>': '1f335',
        '<Cactus>': '1f335',
        '<礼盒>': '1f49d',
        '<禮盒>': '1f49d',
        '<Candy Box>': '1f49d',
        '<南瓜灯>': '1f383',
        '<南瓜燈>': '1f383',
        '<Jack-o-lantern>': '1f383',
        '<鬼魂>': '1f47b',
        '<Ghost>': '1f47b',
        '<圣诞老人>': '1f385',
        '<聖誕老人>': '1f385',
        '<Santa>': '1f385',
        '<圣诞树>': '1f384',
        '<聖誕樹>': '1f384',
        '<Xmas Tree>': '1f384',
        '<礼物>': '1f381',
        '<禮物>': '1f381',
        '<Gift>': '1f381',
        '<铃>': '1f514',
        '<鈴鐺>': '1f514',
        '<Bell>': '1f514',
        '<庆祝>': '1f389',
        '<慶祝>': '1f389',
        '<Party>': '1f389',
        '<气球>': '1f388',
        '<氣球>': '1f388',
        '<Balloon>': '1f388',
        '<CD>': '1f4bf',
        '<相机>': '1f4f7',
        '<相機>': '1f4f7',
        '<Camera>': '1f4f7',
        '<录像机>': '1f3a5',
        '<錄影機>': '1f3a5',
        '<Film Camera>': '1f3a5',
        '<电脑>': '1f4bb',
        '<電腦>': '1f4bb',
        '<Computer>': '1f4bb',
        '<电视>': '1f4fa',
        '<電視>': '1f4fa',
        '<TV>': '1f4fa',
        '<电话>': '1f4de',
        '<電話>': '1f4de',
        '<Phone>': '1f4de',
        '<解锁>': '1f513',
        '<解鎖>': '1f513',
        '<Unlocked>': '1f513',
        '<锁>': '1f512',
        '<鎖>': '1f512',
        '<Locked>': '1f512',
        '<钥匙>': '1f511',
        '<鑰匙>': '1f511',
        '<Key>': '1f511',
        '<成交>': '1f528',
        '<Judgement>': '1f528',
        '<灯泡>': '1f4a1',
        '<燈泡>': '1f4a1',
        '<Light bulb>': '1f4a1',
        '<邮箱>': '1f4eb',
        '<郵箱>': '1f4eb',
        '<Mail>': '1f4eb',
        '<浴缸>': '1f6c0',
        '<Wash>': '1f6c0',
        '<钱>': '1f4b2',
        '<錢>': '1f4b2',
        '<Money>': '1f4b2',
        '<炸弹>': '1f4a3',
        '<炸彈>': '1f4a3',
        '<Bomb>': '1f4a3',
        '<手枪>': '1f52b',
        '<手槍>': '1f52b',
        '<Pistol>': '1f52b',
        '<药丸>': '1f48a',
        '<藥丸>': '1f48a',
        '<Pill>': '1f48a',
        '<橄榄球>': '1f3c8',
        '<橄欖球>': '1f3c8',
        '<Football>': '1f3c8',
        '<篮球>': '1f3c0',
        '<籃球>': '1f3c0',
        '<Basketball>': '1f3c0',
        '<足球>': '26bd',
        '<Soccer Ball>': '26bd',
        '<Soccer>': '26bd',
        '<棒球>': '26be',
        '<Baseball>': '26be',
        '<高尔夫>': '26f3',
        '<高爾夫>': '26f3',
        '<Golf>': '26f3',
        '<奖杯>': '1f3c6',
        '<獎盃>': '1f3c6',
        '<Trophy>': '1f3c6',
        '<入侵者>': '1f47e',
        '<Invader>': '1f47e',
        '<唱歌>': '1f3a4',
        '<Singing>': '1f3a4',
        '<吉他>': '1f3b8',
        '<Guitar>': '1f3b8',
        '<比基尼>': '1f459',
        '<Bikini>': '1f459',
        '<皇冠>': '1f451',
        '<Crown>': '1f451',
        '<雨伞>': '1f302',
        '<雨傘>': '1f302',
        '<Umbrella>': '1f302',
        '<手提包>': '1f45c',
        '<Purse>': '1f45c',
        '<口红>': '1f484',
        '<口紅>': '1f484',
        '<Lipstick>': '1f484',
        '<戒指>': '1f48d',
        '<Ring>': '1f48d',
        '<钻石>': '1f48e',
        '<鑽石>': '1f48e',
        '<Gem>': '1f48e',
        '<咖啡>': '2615',
        '<Coffee>': '2615',
        '<啤酒>': '1f37a',
        '<Beer>': '1f37a',
        '<干杯>': '1f37b',
        '<乾杯>': '1f37b',
        '<Toast>': '1f37b',
        '<鸡尾酒>': '1f377',
        '<雞尾酒>': '1f377',
        '<Martini>': '1f377',
        '<汉堡>': '1f354',
        '<漢堡>': '1f354',
        '<Burger>': '1f354',
        '<薯条>': '1f35f',
        '<薯條>': '1f35f',
        '<Fries>': '1f35f',
        '<意面>': '1f35d',
        '<意粉>': '1f35d',
        '<Sphaghetti>': '1f35d',
        '<寿司>': '1f363',
        '<壽司>': '1f363',
        '<Sushi>': '1f363',
        '<面条>': '1f35c',
        '<麵條>': '1f35c',
        '<Noodles>': '1f35c',
        '<煎蛋>': '1f373',
        '<Eggs>': '1f373',
        '<冰激凌>': '1f366',
        '<雪糕>': '1f366',
        '<Ice Cream>': '1f366',
        '<蛋糕>': '1f382',
        '<Cake>': '1f382',
        '<苹果>': '1f34f',
        '<蘋果>': '1f34f',
        '<Apple>': '1f34f',
        '<飞机>': '2708',
        '<飛機>': '2708',
        '<Plane>': '2708',
        '<火箭>': '1f680',
        '<Rocket ship>': '1f680',
        '<自行车>': '1f6b2',
        '<單車>': '1f6b2',
        '<Bike>': '1f6b2',
        '<高铁>': '1f684',
        '<高鐵>': '1f684',
        '<Bullet Train>': '1f684',
        '<警告>': '26a0',
        '<Warning>': '26a0',
        '<旗>': '1f3c1',
        '<Flag>': '1f3c1',
        '<男人>': '1f6b9',
        '<男>': '1f6b9',
        '<Men>': '1f6b9',
        '<女人>': '1f6ba',
        '<女>': '1f6ba',
        '<Women>': '1f6ba',
        '<O>': '2b55',
        '<X>': '274e',
        '<版权>': 'a9',
        '<版權>': 'a9',
        '<Copyright>': 'a9',
        '<注册商标>': 'ae',
        '<注冊商標>': 'ae',
        '<Registered TM>': 'ae',
        '<商标>': '2122',
        '<商標>': '2122',
        '<Trademark>': '2122' }
    }
  },
  computed: {
    ...mapGetters({
      personalInfo: 'personalInfo',
      currentCircles: 'circles/currentCircles',
      showFriendCircle: 'circles/showFriendCircle'
    })
  },
  methods: {
    ...mapActions({
      SetShowFriendCircle: 'circles/SetShowFriendCircle'
    }),
    // 显示或隐藏表情
    toggleExpression () {
      let flag = this.showExpression
      this.showExpression = !flag
    },
    // 选择表情
    selectExpression (val) {
      // console.log('选择表情')
      this.showExpression = false
      let rm = this.expressionMap[val]
      let qqList = [113, 114, 115, 116, 117, 118, 124, 125]
      // qq表情
      if (rm) {
        if (val < 105 || qqList.indexOf(val) >= 0) {
          rm = '[' + this.expressionMap[val] + ']'
        } else {
          rm = '<' + rm + '>'
          rm = this.emojiMap[rm]
          // let en = '😁'
          // let encode = en.codePointAt(0)
          let rmencode = parseInt(rm, 16)
          rm = String.fromCodePoint(rmencode)
        }
        this.toolsContent.contentType = 1
        this.toolsContent.content = rm
        this.toolsContent.name = ''
        this.toolsContent.size = 0
        this.toolsContent.fileType = ''
        this.$emit('tools-content', this.toolsContent)
        this.toolsContent = {}
      } else {
        console.log('无效选择')
      }
    },
    // 发图片
    sendMedia () {
      console.log('发送多媒体文件')
      let formdata = new FormData()
      let fileObj = this.$refs.toolsmedia.files
      // console.log(fileObj[0])
      if (fileObj[0]) {
        let name = fileObj[0].name
        let size = fileObj[0].size
        let type = 2
        let fileType = name.replace(/.+\./, '').toLowerCase()
        switch (fileType) {
          case 'image':
          case 'jpg':
          case 'png':
          case 'gif':
          case 'jpeg':
            type = 2
            break
          case 'mp4':
            type = 4
            break
          default:
            type = 0
            break
        }
        if (size < 104857600 && type !== 0) {
          formdata.append('myfile', fileObj[0])
          this.$store.dispatch('UploadFile', formdata).then(
            res => {
              this.toolsContent.contentType = type
              this.toolsContent.content = res.url
              this.toolsContent.name = name
              this.toolsContent.size = size
              this.toolsContent.fileType = fileType
              this.$emit('tools-content', this.toolsContent)
              this.$refs.toolsmedia.value = ''
              this.toolsContent = {}
            }
          ).catch(err => console.log(err))
        } else {
          if (type === 0) {
            alert('不支持的文件格式')
          } else {
            alert('文件不能大于100M')
          }
        }
      } else {
        console.log('未选择文件')
      }
    },
    // 发文件
    sendFile () {
      console.log('发送文件')
      let formdata = new FormData()
      let fileObj = this.$refs.toolsFile.files
      // console.log(fileObj[0])
      if (fileObj[0]) {
        let name = fileObj[0].name
        let size = fileObj[0].size
        let type = 8
        if (size < 104857600) {
          formdata.append('myfile', fileObj[0])
          this.$store.dispatch('UploadFile', formdata).then(
            res => {
              this.toolsContent.content = res.url
              this.toolsContent.contentType = type
              this.toolsContent.name = name
              this.toolsContent.size = size
              this.toolsContent.fileType = name.replace(/.+\./, '').toLowerCase()
              this.$emit('tools-content', this.toolsContent)
              this.$refs.toolsmedia.value = ''
              this.toolsContent = {}
            }
          ).catch(err => console.log(err))
        } else {
          alert('文件不能大于100M')
        }
      } else {
        console.log('未选择文件')
      }
    },
    // 显示/隐藏好友的朋友圈
    showFC () {
      let flag = this.showFriendCircle
      this.SetShowFriendCircle(!flag)
      // let count = 5
      if (this.currentCircles && this.currentCircles.length > 0) {
        console.log('本地有数据')
        // console.log(this.currentCircles[0].publishTime)
        // let lastTime = this.currentCircles[0].publishTime
        // console.log(Math.floor(new Date().getTime() / 1000))
        // let currentTime = Math.floor(new Date().getTime() / 1000)
        // let t = Math.floor((currentTime - lastTime) / 14400)
        // console.log(t)
        // console.log(t / 3600)
        // if (this.personalInfo && this.personalInfo.wechatId) {
        //   let startTime = Math.floor(new Date().getTime() / 1000)
        //   let content = { WeChatId: this.personalInfo.wechatId, FriendId: this.personalInfo.friendId, StartTime: startTime, Count: 5 }
        //   let msg = { 'MsgType': 'PullFriendCircleTask', 'Content': content }
        //   this.socketApi.sendSock(msg)
        // } else {
        //   alert('请先选择好友')
        // }
      } else {
        console.log('本地没有该好友的朋友圈数据')
        if (this.personalInfo && this.personalInfo.wechatId) {
          let startTime = Math.floor(new Date().getTime() / 1000)
          let content = { WeChatId: this.personalInfo.wechatId, FriendId: this.personalInfo.friendId, StartTime: startTime, Count: 5 }
          let msg = { 'MsgType': 'PullFriendCircleTask', 'Content': content }
          this.socketApi.sendSock(msg)
        } else {
          alert('请先选择好友')
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.toolbar {
  height: 5%;
  width: 98%;
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.expressions {
  width: 385px;
  height: 210px;
  overflow: auto;
  background: white;
  position: absolute;
  top: -220px;
  left: 0;
  padding: 5px;
  img {
    width: 375px;
  }
}

.tool1 {
  width: 200px;
  height: 30px;
  display: flex;
  align-items: center;
  i {
    font-size: 20px;
    cursor: pointer;
    &:hover {
      color: #46b6ef;
    }
  }
  .icon {
    cursor: pointer;
  }
}

.tool2 {
  width: 12%;
  justify-content: space-around;
  div {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>



