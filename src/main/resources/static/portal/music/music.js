$(function() {
    musicManager.getMusicList();
});
var musicManager = {
    getMusicList: function () {
        $.getJSON("/musicAll/EDM",function (resp) {
            if (resp.code == 200) {
                const player = new APlayer({
                    element: document.getElementById('player'),
                    mini: false,
                    autoplay: false,
                    lrcType: false,
                    mutex: true,
                    theme: '#ad7a86',
                    order: 'list',
                    audio: resp.data
                })
            } else {
                swal("查询失败", resp.msg,"error");
            }
        });
    },
}
// const player = new APlayer({
//     element: document.getElementById('player'),
//     mini: false,
//     autoplay: false,
//     lrcType: false,
//     mutex: true,
//     theme: '#ad7a86',
//     order: 'list',
//     audio: [{
//         name: '万有引力',
//         artist: 'Fyy',
//         url: 'http://120.25.235.181:51213/Fyy - 万有引力.mp3',
//         cover: './music/cover/fyy.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '莫问归期',
//         artist: '蒋雪儿',
//         url: 'http://120.25.235.181:51213/蒋雪儿 - 莫问归期.mp3',
//         cover: './music/cover/jiangxueer.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '点歌的人',
//         artist: '海来阿木',
//         url: 'http://120.25.235.181:51213/海来阿木 - 点歌的人.mp3',
//         cover: './music/cover/hailaiamu.jpg',
//         theme: '#46718b'
//     }, {
//         name: '后来遇见他',
//         artist: '胡66',
//         url: 'http://120.25.235.181:51213/胡66 - 后来遇见他.mp3',
//         cover: './music/cover/hu66.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '赤伶',
//         artist: 'HITA',
//         url: 'http://120.25.235.181:51213/HITA - 赤伶.mp3',
//         cover: './music/cover/hita.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '嚣张',
//         artist: '祁闻冥轩',
//         url: 'http://120.25.235.181:51213/祁闻冥轩 - 嚣张.mp3',
//         cover: './music/cover/qiwenmingxuan.jpg',
//         theme: '#46718b'
//     }, {
//         name: '少年',
//         artist: '梦然',
//         url: 'http://120.25.235.181:51213/梦然 - 少年.mp3',
//         cover: './music/cover/mengran.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '一个人挺好',
//         artist: '周森',
//         url: 'http://120.25.235.181:51213/周森 - 一个人挺好.mp3',
//         cover: './music/cover/zhoushen.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '你笑起来真好看',
//         artist: '刘苏萱',
//         url: 'http://120.25.235.181:51213/刘苏萱 - 你笑起来真好看.mp3',
//         cover: './music/cover/liusuxuan.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '世界这么大还是遇见你',
//         artist: '程响',
//         url: 'http://120.25.235.181:51213/程响 - 世界这么大还是遇见你.mp3',
//         cover: './music/cover/chengxiang.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '百花香',
//         artist: '魏新雨',
//         url: 'http://120.25.235.181:51213/魏新雨 - 百花香.mp3',
//         cover: './music/cover/weixinyu.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '下山',
//         artist: '要不要买菜',
//         url: 'http://120.25.235.181:51213/要不要买菜 - 下山.mp3',
//         cover: './music/cover/ybymc.jpg',
//         theme: '#46718b'
//     }, {
//         name: '桥边姑娘',
//         artist: '张茜',
//         url: 'http://120.25.235.181:51213/张茜 - 桥边姑娘.mp3',
//         cover: './music/cover/zhangqian.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '你的答案',
//         artist: '阿冗',
//         url: 'http://120.25.235.181:51213/阿冗 - 你的答案.mp3',
//         cover: './music/cover/arong.jpg',
//         theme: '#46718b'
//     }, {
//         name: '生而为人',
//         artist: '尚士达',
//         url: 'http://120.25.235.181:51213/尚士达 - 生而为人.mp3',
//         cover: './music/cover/shangshida.jpg',
//         theme: '#46718b'
//     }, {
//         name: '火红的萨日朗',
//         artist: '要不要买菜',
//         url: 'http://120.25.235.181:51213/要不要买菜 - 火红的萨日朗.mp3',
//         cover: './music/cover/ybymc.jpg',
//         theme: '#46718b'
//     }, {
//         name: '画',
//         artist: '韩甜甜',
//         url: 'http://120.25.235.181:51213/韩甜甜 - 画.mp3',
//         cover: './music/cover/hantiantian.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '飞云之下',
//         artist: '韩甜甜',
//         url: 'http://120.25.235.181:51213/韩甜甜 - 飞云之下.mp3',
//         cover: './music/cover/hantiantian.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '世间美好与你环环相扣',
//         artist: '柏松',
//         url: 'http://120.25.235.181:51213/柏松 - 世间美好与你环环相扣.mp3',
//         cover: './music/cover/baisong.jpg',
//         theme: '#46718b'
//     }, {
//         name: '大田後生仔',
//         artist: '丫蛋蛋',
//         url: 'http://120.25.235.181:51213/丫蛋蛋 - 大田後生仔.mp3',
//         cover: './music/cover/yadandan.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '野狼disco',
//         artist: '宝石Gem',
//         url: 'http://120.25.235.181:51213/宝石Gem - 野狼disco.mp3',
//         cover: './music/cover/gem.jpg',
//         theme: '#46718b'
//     }, {
//         name: '像我这样的人',
//         artist: '毛不易',
//         url: 'http://120.25.235.181:51213/毛不易 - 像我这样的人.mp3',
//         cover: './music/cover/maobuyi.jpg',
//         theme: '#46718b'
//     }, {
//         name: '孤芳自赏',
//         artist: '杨小壮',
//         url: 'http://120.25.235.181:51213/杨小壮 - 孤芳自赏.mp3',
//         cover: './music/cover/yangxiaozhuang.jpg',
//         theme: '#46718b'
//     }, {
//         name: '别想她',
//         artist: '小阿枫',
//         url: 'http://120.25.235.181:51213/小阿枫 - 别想她.mp3',
//         cover: './music/cover/xiaoafeng.jpg',
//         theme: '#46718b'
//     }, {
//         name: '最远的你是我最近的爱',
//         artist: '小阿枫',
//         url: 'http://120.25.235.181:51213/小阿枫 - 最远的你是我最近的爱.mp3',
//         cover: './music/cover/xiaoafeng.jpg',
//         theme: '#46718b'
//     }, {
//         name: '想死却又不敢',
//         artist: '井胧',
//         url: 'http://120.25.235.181:51213/井胧 - 想死却又不敢.mp3',
//         cover: './music/cover/jinglong.jpg',
//         theme: '#46718b'
//     }, {
//         name: '把孤独当做晚餐',
//         artist: '南铃子',
//         url: 'http://120.25.235.181:51213/南铃子 - 把孤独当做晚餐.mp3',
//         cover: './music/cover/nanlingzi.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: 'Lil Mama',
//         artist: 'Jain',
//         url: 'http://120.25.235.181:51213/Jain - Lil Mama.mp3',
//         cover: './music/cover/Jain.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '说好不哭',
//         artist: '周杰伦',
//         url: 'http://120.25.235.181:51213/周杰伦 - 说好不哭.mp3',
//         cover: './music/cover/zhoujielun.jpg',
//         theme: '#46718b'
//     }, {
//         name: '很任性',
//         artist: '兔子牙',
//         url: 'http://120.25.235.181:51213/兔子牙 - 很任性.mp3',
//         cover: './music/cover/tuziya.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '小白兔遇上卡布奇诺',
//         artist: '兔子牙',
//         url: 'http://120.25.235.181:51213/兔子牙 - 小白兔遇上卡布奇诺.mp3',
//         cover: './music/cover/tuziya.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '红昭愿',
//         artist: '音阙诗听',
//         url: 'http://120.25.235.181:51213/音阙诗听 - 红昭愿.mp3',
//         cover: './music/cover/yinqueshiting.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '我曾',
//         artist: '隔壁老樊',
//         url: 'http://120.25.235.181:51213/隔壁老樊 - 我曾.mp3',
//         cover: './music/cover/gebilaofan.jpg',
//         theme: '#46718b'
//     }, {
//         name: '多想在平庸的生活拥抱你',
//         artist: '隔壁老樊',
//         url: 'http://120.25.235.181:51213/隔壁老樊 - 多想在平庸的生活拥抱你.mp3',
//         cover: './music/cover/gebilaofan.jpg',
//         theme: '#46718b'
//     }, {
//         name: '走马',
//         artist: '摩登兄弟',
//         url: 'http://120.25.235.181:51213/摩登兄弟 - 走马.mp3',
//         cover: './music/cover/modengxiongdi.jpg',
//         theme: '#46718b'
//     }, {
//         name: '病变',
//         artist: '江澈',
//         url: 'http://120.25.235.181:51213/江澈 - 病变.mp3',
//         cover: './music/cover/jiangche.jpg',
//         theme: '#46718b'
//     }, {
//         name: '浪人琵琶',
//         artist: '胡66',
//         url: 'http://120.25.235.181:51213/胡66 - 浪人琵琶.mp3',
//         cover: './music/cover/hu66.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '芒种',
//         artist: '赵方婧',
//         url: 'http://120.25.235.181:51213/赵方婧 - 芒种.mp3',
//         cover: './music/cover/zhaofangqian.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '逆流成河',
//         artist: '金南玲',
//         url: 'http://120.25.235.181:51213/金南玲 - 逆流成河.mp3',
//         cover: './music/cover/jinnanling.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '写给黄淮',
//         artist: '张怡',
//         url: 'http://120.25.235.181:51213/张怡 - 写给黄淮.mp3',
//         cover: './music/cover/zhangyi.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '东西',
//         artist: '林俊呈',
//         url: 'http://120.25.235.181:51213/林俊呈 - 东西.mp3',
//         cover: './music/cover/linjunchen.jpg',
//         theme: '#46718b'
//     }, {
//         name: '选择失忆',
//         artist: '季彦霖',
//         url: 'http://120.25.235.181:51213/季彦霖 - 选择失忆.mp3',
//         cover: './music/cover/bukaopu.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '38度6',
//         artist: '黑龙',
//         url: 'http://120.25.235.181:51213/黑龙 - 38度6.mp3',
//         cover: './music/cover/heilong.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '童话镇',
//         artist: '陈一发',
//         url: 'http://120.25.235.181:51213/陈一发 - 童话镇.mp3',
//         cover: './music/cover/chenyifa.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '夜之光',
//         artist: '花姐',
//         url: 'http://120.25.235.181:51213/花姐 - 夜之光.mp3',
//         cover: './music/cover/huajie.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '那个人',
//         artist: '周延英',
//         url: 'http://120.25.235.181:51213/周延英 - 那个人.mp3',
//         cover: './music/cover/zhouyanying.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '9420',
//         artist: '麦小兜',
//         url: 'http://120.25.235.181:51213/麦小兜 - 9420.mp3',
//         cover: './music/cover/maixiaodou.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '春风吹',
//         artist: '阿冷',
//         url: 'http://120.25.235.181:51213/阿冷 - 春风吹.mp3',
//         cover: './music/cover/aleng.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '来自天堂的魔鬼',
//         artist: '邓紫棋',
//         url: 'http://120.25.235.181:51213/邓紫棋 - 来自天堂的魔鬼.mp3',
//         cover: './music/cover/dengziqi.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '倒数',
//         artist: '邓紫棋',
//         url: 'http://120.25.235.181:51213/邓紫棋 - 倒数.mp3',
//         cover: './music/cover/dengziqi.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '光年之外',
//         artist: '邓紫棋',
//         url: 'http://120.25.235.181:51213/邓紫棋 - 光年之外.mp3',
//         cover: './music/cover/dengziqi.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '不再犹豫',
//         artist: 'Beyond',
//         url: 'http://120.25.235.181:51213/Beyond - 不再犹豫.mp3',
//         cover: './music/cover/Beyond.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '大地',
//         artist: 'Beyond',
//         url: 'http://120.25.235.181:51213/Beyond - 大地.mp3',
//         cover: './music/cover/Beyond.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '光辉岁月',
//         artist: 'Beyond',
//         url: 'http://120.25.235.181:51213/Beyond - 光辉岁月.mp3',
//         cover: './music/cover/Beyond.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '海阔天空',
//         artist: 'Beyond',
//         url: 'http://120.25.235.181:51213/Beyond - 海阔天空.mp3',
//         cover: './music/cover/Beyond.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '灰色轨迹',
//         artist: 'Beyond',
//         url: 'http://120.25.235.181:51213/Beyond - 灰色轨迹.mp3',
//         cover: './music/cover/Beyond.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '冷雨夜',
//         artist: 'Beyond',
//         url: 'http://120.25.235.181:51213/Beyond - 冷雨夜.mp3',
//         cover: './music/cover/Beyond.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '情人',
//         artist: 'Beyond',
//         url: 'http://120.25.235.181:51213/Beyond - 情人.mp3',
//         cover: './music/cover/Beyond.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '谁伴我闯荡',
//         artist: 'Beyond',
//         url: 'http://120.25.235.181:51213/Beyond - 谁伴我闯荡.mp3',
//         cover: './music/cover/Beyond.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '喜欢你',
//         artist: 'Beyond',
//         url: 'http://120.25.235.181:51213/Beyond - 喜欢你.mp3',
//         cover: './music/cover/Beyond.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '真的爱你',
//         artist: 'Beyond',
//         url: 'http://120.25.235.181:51213/Beyond - 真的爱你.mp3',
//         cover: './music/cover/Beyond.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: 'The Spectre',
//         artist: 'Alan Walker',
//         url: 'http://120.25.235.181:51213/Alan Walker - The Spectre.mp3',
//         cover: './music/cover/AlanWalker.jpg',
//         theme: '#46718b'
//     },  {
//         name: 'All Falls Down',
//         artist: 'Alan Walker',
//         url: 'http://120.25.235.181:51213/Alan Walker - All Falls Down.mp3',
//         cover: './music/cover/AlanWalker.jpg',
//         theme: '#46718b'
//     },  {
//         name: 'Alone',
//         artist: 'Alan Walker',
//         url: 'http://120.25.235.181:51213/Alan Walker - Alone.mp3',
//         cover: './music/cover/AlanWalker.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Faded',
//         artist: 'Alan Walker',
//         url: 'http://120.25.235.181:51213/Alan Walker - Faded.mp3',
//         cover: './music/cover/AlanWalker.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'PDD 洪荒之力',
//         artist: 'Hoaprox',
//         url: 'http://120.25.235.181:51213/Hoaprox - PDD 洪荒之力.mp3',
//         cover: './music/cover/Hoaprox.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Kattrina',
//         artist: 'Fly Project',
//         url: 'http://120.25.235.181:51213/Fly Project - Kattrina.mp3',
//         cover: './music/cover/FlyProject.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Zuehlsdorff',
//         artist: 'Vicetone',
//         url: 'http://120.25.235.181:51213/Vicetone - Zuehlsdorff.mp3',
//         cover: './music/cover/Vicetone.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Run Free',
//         artist: 'Deep Chills',
//         url: 'http://120.25.235.181:51213/Deep Chills - Run Free.mp3',
//         cover: './music/cover/DeepChills.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Something Just Like This',
//         artist: 'The Chainsmokers',
//         url: 'http://120.25.235.181:51213/The Chainsmokers - Something Just Like This.mp3',
//         cover: './music/cover/TheChainsmokers.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Wild',
//         artist: 'Monogem',
//         url: 'http://120.25.235.181:51213/Monogem - Wild.mp3',
//         cover: './music/cover/Monogem.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Sunshine Girl',
//         artist: 'Moumoon',
//         url: 'http://120.25.235.181:51213/Moumoon - Sunshine Girl.mp3',
//         cover: './music/cover/Moumoon.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Friendships (Original Mix)',
//         artist: 'Pascal Letoublon',
//         url: 'http://120.25.235.181:51213/Pascal Letoublon - Friendships (Original Mix).mp3',
//         cover: './music/cover/PascalLetoublon.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Williams - Lullaby',
//         artist: 'R3hab,Mike',
//         url: 'http://120.25.235.181:51213/R3hab,Mike Williams - Lullaby.mp3',
//         cover: './music/cover/R3hab.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Move Your Body (Alan Walker Remix)',
//         artist: 'Sia,Alan Walker',
//         url: 'http://120.25.235.181:51213/Sia,Alan Walker - Move Your Body (Alan Walker Remix).mp3',
//         cover: './music/cover/AlanWalker.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Unity',
//         artist: 'The Fat Rat',
//         url: 'http://120.25.235.181:51213/The Fat Rat - Unity.mp3',
//         cover: './music/cover/TheFatRat.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'Seve',
//         artist: 'Tez Cadey',
//         url: 'http://120.25.235.181:51213/Tez Cadey - Seve.mp3',
//         cover: './music/cover/TezCadey.jpg',
//         theme: '#46718b'
//     }, {
//         name: '一曲相思',
//         artist: '阿悠悠',
//         url: 'http://120.25.235.181:51213/阿悠悠 - 一曲相思.mp3',
//         cover: './music/cover/ayouyou.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '流浪',
//         artist: '半阳',
//         url: 'http://120.25.235.181:51213/半阳 - 流浪.mp3',
//         cover: './music/cover/banyang.jpg',
//         theme: '#46718b'
//     }, {
//         name: '画',
//         artist: '赵雷',
//         url: 'http://120.25.235.181:51213/赵雷 - 画.mp3',
//         cover: './music/cover/zhaolei.jpg',
//         theme: '#46718b'
//     }, {
//         name: '说书人',
//         artist: '暗杠,寅子',
//         url: 'http://120.25.235.181:51213/暗杠,寅子 - 说书人.mp3',
//         cover: './music/cover/angang.jpg',
//         theme: '#46718b'
//     }, {
//         name: '不染',
//         artist: '简弘亦',
//         url: 'http://120.25.235.181:51213/简弘亦 - 不染.mp3',
//         cover: './music/cover/jianhongyi.jpg',
//         theme: '#46718b'
//     }, {
//         name: '陷阱',
//         artist: '王北车',
//         url: 'http://120.25.235.181:51213/王北车 - 陷阱.mp3',
//         cover: './music/cover/wangbeiche.jpg',
//         theme: '#46718b'
//     }, {
//         name: '遥远的你',
//         artist: '不靠谱组合',
//         url: 'http://120.25.235.181:51213/不靠谱组合 - 遥远的你.mp3',
//         cover: './music/cover/bukaopu.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '月牙湾',
//         artist: '刘至佳',
//         url: 'http://120.25.235.181:51213/刘至佳 - 月牙湾.mp3',
//         cover: './music/cover/liuzhijia.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '去年夏天',
//         artist: '王大毛',
//         url: 'http://120.25.235.181:51213/王大毛 - 去年夏天.mp3',
//         cover: './music/cover/wangdamao.jpg',
//         theme: '#46718b'
//     }, {
//         name: '带你去旅行',
//         artist: '校长',
//         url: 'http://120.25.235.181:51213/校长 - 带你去旅行.mp3',
//         cover: './music/cover/xiaozhang.jpg',
//         theme: '#46718b'
//     }, {
//         name: '心如止水',
//         artist: 'Ice Paper',
//         url: 'http://120.25.235.181:51213/Ice Paper - 心如止水.mp3',
//         cover: './music/cover/IcePape.jpg',
//         theme: '#46718b'
//     }, {
//         name: '侧脸',
//         artist: '于果',
//         url: 'http://120.25.235.181:51213/于果 - 侧脸.mp3',
//         cover: './music/cover/yuguo.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '绿色',
//         artist: '陈雪凝',
//         url: 'http://120.25.235.181:51213/陈雪凝 - 绿色.mp3',
//         cover: './music/cover/chenxuening.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '出山',
//         artist: '花粥',
//         url: 'http://120.25.235.181:51213/花粥 - 出山.mp3',
//         cover: './music/cover/huazhou.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '遥不可及的你',
//         artist: '花粥',
//         url: 'http://120.25.235.181:51213/花粥 - 遥不可及的你.mp3',
//         cover: './music/cover/huazhou.jpg',
//         theme: '#ebd0c2'
//     }, {
//         name: '相思如意',
//         artist: '兔子牙',
//         url: 'http://120.25.235.181:51213/兔子牙 - 相思如意.mp3',
//         cover: './music/cover/tuziya.jpg',
//         theme: '#505d6b'
//     }, {
//         name: '江南夜色',
//         artist: '程响',
//         url: 'http://120.25.235.181:51213/程响 - 江南夜色.mp3',
//         cover: './music/cover/chengxiang.jpg',
//         theme: '#46718b'
//     }, {
//         name: '过客',
//         artist: '周思涵',
//         url: 'http://120.25.235.181:51213/周思涵 - 过客.mp3',
//         cover: './music/cover/zhousihan.jpg',
//         theme: '#505d6b'
//     }, {
//         name: '年少有为',
//         artist: '李荣浩',
//         url: 'http://120.25.235.181:51213/李荣浩 - 年少有为.mp3',
//         cover: './music/cover/lironghao.jpg',
//         theme: '#46718b'
//     }, {
//         name: '曾经的你',
//         artist: '许巍',
//         url: 'http://120.25.235.181:51213/许巍 - 曾经的你.mp3',
//         cover: './music/cover/xuwei.jpg',
//         theme: '#46718b'
//     }, {
//         name: '出现又离开',
//         artist: '梁博',
//         url: 'http://120.25.235.181:51213/梁博 - 出现又离开.mp3',
//         cover: './music/cover/liangbo.jpg',
//         theme: '#46718b'
//     }, {
//         name: 'A Little Story',
//         artist: 'Valentin',
//         url: 'http://120.25.235.181:51213/Valentin - A Little Story.mp3',
//         cover: './music/cover/Valentin.jpg',
//         theme: '#46718b'
//     }]
// });
