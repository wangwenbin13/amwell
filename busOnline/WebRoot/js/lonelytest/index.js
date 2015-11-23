
$(function() {
      // 点击开始测试
      $('#index .btn').click(function () {
            $('#index').attr('hidden', 'hidden');
            $('#test').removeAttr('hidden');
      });

      // 点击查看结果
      $('#test .btn').click(function () {
            addResult(resultIndex);
            $('#test').attr('hidden', 'hidden');
            $('#result').removeAttr('hidden');
      });

      // 分享弹窗
      $('.share').click(function () {
            $('#model').show();
            $('#share-layout').show();
      });
      $('.iknow').click(function () {
            $('#model').hide();
            $('#share-layout').hide();
      })


      var al_title = {
            'a0': '一级孤独',
            'a1': '三级孤独',
            'a2': '五级孤独',
            'a3': '八级孤独',
      },
      al_desc1 = {
            'a0': '朕的字典里没有“孤独”二字，我只是来打酱油的！',
            'a1': '作为孤独的轻度伤残患者，快来陪我过家家~',
            'a2': '天呐，你一定是嫌弃我长得帅，所以不敢来和我一起看电影。噢！好忧伤~',
            'a3': '从现在开始我希望能慢慢变丑点，不然我一定会被粉丝们给非礼了，我怕怕。',
      },
      al_desc2 = {
            'a0': '熊孩子，你是来凑热闹的吧',
            'a1': '我从小学到初中喜欢过五个女孩，但表白都失败了，昨天晚上我TMD又被拒绝了',
            'a2': '什么叫寂寞？就是50元话费居然用了三个月还没用完',
            'a3': '我越来越喜欢推销保险的妹子给我打电话了因为除了10086她们是唯一给我打电话的女性了',
      };

      function addResult (result) {
            $('#result .al-title').html(al_title[result]);
            $('#result .al-desc1').html(al_desc1[result]);
            $('#result .al-desc2').html(al_desc2[result]);
      }
      
      window.$RESULT = result;
});
