/**
 *
 */

function checkBillingAddress() {
    if($("#theSameAsShippingAddress").is(":checked")) {
        $(".billingAddress").prop("disabled", true);
    } else {
        $(".billingAddress").prop("disabled", false);
    }
}

function checkPasswordMatch() {
    var password = $("#txtNewPassword").val();
    var confirmPassword = $("#txtConfirmPassword").val();

    if(password == "" && confirmPassword =="") {
        $("#checkPasswordMatch").html("");
        $("#updateUserInfoButton").prop('disabled', false);
    } else {
        if(password != confirmPassword) {
            $("#checkPasswordMatch").html("Passwords do not match!");
            $("#updateUserInfoButton").prop('disabled', true);
        } else {
            $("#checkPasswordMatch").html("Passwords match");
            $("#updateUserInfoButton").prop('disabled', false);
        }
    }
}

$(document).ready(function(){
    $(".cartItemQty").on('change', function(){
        var id=this.id;

        $('#update-item-'+id).css('display', 'inline-block');
    });
    $("#theSameAsShippingAddress").on('click', checkBillingAddress);
    $("#txtConfirmPassword").keyup(checkPasswordMatch);
    $("#txtNewPassword").keyup(checkPasswordMatch);
});


/**
 *
 */

$(document).ready(function() {
    $('.delete-book').on('click', function (){
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'remove';
        /*]]>*/

        var id=$(this).attr('id');

        bootbox.confirm({
            message: "你真的確定要刪除這個嗎？",
            buttons: {
                cancel: {
                    label:'<i class="fa fa-times"></i> 取消'
                },
                confirm: {
                    label:'<i class="fa fa-check"></i> 確定'
                }
            },
            callback: function(confirmed) {
                if(confirmed) {
                    $.post(path, {'id':id}, function(res) {
                        location.reload();
                    });
                }
            }
        });
    });



//	$('.checkboxBook').click(function () {
//        var id = $(this).attr('id');
//        if(this.checked){
//            bookIdList.push(id);
//        }
//        else {
//            bookIdList.splice(bookIdList.indexOf(id), 1);
//        }
//    })

    $('#deleteSelected').click(function() {
        var idList= $('.checkboxBook');
        var bookIdList=[];
        for (var i = 0; i < idList.length; i++) {
            if(idList[i].checked==true) {
                bookIdList.push(idList[i]['id'])
            }
        }

        console.log(bookIdList);

        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'removeList';
        /*]]>*/

        bootbox.confirm({
            message: "你確定要刪除所許的所有項目？",
            buttons: {
                cancel: {
                    label:'<i class="fa fa-times"></i> 取消'
                },
                confirm: {
                    label:'<i class="fa fa-check"></i> 確定'
                }
            },
            callback: function(confirmed) {
                if(confirmed) {
                    $.ajax({
                        type: 'POST',
                        url: path,
                        data: JSON.stringify(bookIdList),
                        contentType: "application/json",
                        success: function(res) {
                            console.log(res);
                            location.reload()
                        },
                        error: function(res){
                            console.log(res);
                            location.reload();
                        }
                    });
                }
            }
        });
    });

    $("#selectAllBooks").click(function() {
        if($(this).prop("checked")==true) {
            $(".checkboxBook").prop("checked",true);
        } else if ($(this).prop("checked")==false) {
            $(".checkboxBook").prop("checked",false);
        }
    })

    $('.delete-one-book').on('click', function () {
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'remove';
        /*]]>*/

        var id = $(this).attr('id');

        bootbox.confirm({
            message: "你真的確定要刪除這個嗎？",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> 取消'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> 確定'
                }
            },
            callback: function (confirmed) {
                if (confirmed) {
                    $.post(path, {'id': id}, function (res) {
                        history.back();
                    });
                }
            }
        });
    });
});