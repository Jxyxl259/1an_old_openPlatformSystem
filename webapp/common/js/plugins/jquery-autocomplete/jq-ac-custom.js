$(function() {
    function toJSON(data) {
        return eval("(" + data + ")");
    }
    
    var count = 0;
    $("[data-ac]").each(function() {
        var self = $(this);
        var config = toJSON(self.attr("data-ac"));
        var labelKey = config['labelKey'];
        var labelFn = config['labelFn'];
        var valueKey = config['valueKey'];
        var valueFn = config['valueFn'];
        var selectFn = config['selectFn'];
        var dataFn = config['dataFn'];
        var data = config['data'];
        var defaultValueKey = "value";

        if (dataFn) {
            config['getData'] = dataFn;
        } else if (data) {
            config['data'] = window[data];
        } else {
            alert("please config data or dataFn!");
        }
        
        config['processData'] = function(values) {
            var result = [];
            $.each(values, function(index, item) {
                if (valueFn) {
                    result.push({ value : valueFn(item), data : item });
                } else if (valueKey) {
                    result.push({ value : item[valueKey], data : item });
                } else {
                    result.push({ value : item[defaultValueKey], data : item });
                }
            });
            return result;
        };

        if (labelKey || labelFn) {
            config['showResult'] = function(value, data) {
                if (labelFn) {
                    return labelFn(data);
                } else {
                    return data[labelKey]
                }
            };
        }

        if (selectFn) {
            config['onItemSelect'] = selectFn;
        }

        var thisClass = 'cms-auto-complete' + (++count);
        self.attr('ac-result-class', thisClass);
        config.resultsClass = 'acResults ' + thisClass;
        self.autocomplete(config);
    });
    
    $(document).click(function(e) {
        var target = $(e.target);
        var shouldHideAc = $('.acResults:visible');
        if (shouldHideAc.length && target.is('input[data-ac]')) {
            var extClassName = target.attr('ac-result-class');
            shouldHideAc = shouldHideAc.not('.' + extClassName);
        }

        shouldHideAc.each(function() {
            var extClassName = $(this).attr('class').split(' ')[1];
            $('[ac-result-class="' + extClassName + '"]').trigger('ac-finish');
        });
    });
});