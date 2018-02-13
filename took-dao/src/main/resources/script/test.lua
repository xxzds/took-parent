--分割函数
function string.split(str, delimiter)
	if str==nil or str=='' or delimiter==nil then
		return nil
	end
	
    local result = {}
    for match in (str..delimiter):gmatch("(.-)"..delimiter) do
        table.insert(result, match)
    end
    return result
end

--随机获取 分类id_关键字_最大页数
local cateIdKeywordMaxPage = tostring(redis.call('SRANDMEMBER',KEYS[1]))
print(cateIdKeywordMaxPage)
--获取当前页
local currentPage = redis.call('HMGET', KEYS[2], cateIdKeywordMaxPage)[1]
local result = {}
result['currentPage'] = currentPage

local tabl = string.split(cateIdKeywordMaxPage, '_');
result['cateId'] = tabl[1]
result['keyword'] = tabl[2]
local maxPage = tabl[3]

 if tonumber(currentPage) < tonumber(maxPage) then
 	 redis.call('HINCRBY', KEYS[2], cateIdKeywordMaxPage,1)
 else
     redis.call('HSET', KEYS[2], cateIdKeywordMaxPage,1)
 end
 local encodestr = cjson.encode(result)
 print(encodestr)
return  encodestr