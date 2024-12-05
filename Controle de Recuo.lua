--|Copiar e Colar nos Scripts da Loghitech|--
EnableRCS = true
RecoilControlMode = "1"
RcCustomStrength = 7 
RequireToggle = false
ToggleKey = "NumLock"
DelayRate = 7 

if RecoilControlMode == "1" then
    RecoilControlStrength = 18
elseif RecoilControlMode == "2" then
    RecoilControlStrength = 24
elseif RecoilControlMode == "3" then
    RecoilControlStrength = 35
end

EnablePrimaryMouseButtonEvents(true)

function OnEvent(event, arg)
    if EnableRCS then
        if RequireToggle then
            if IsKeyLockOn(ToggleKey) then
                if IsMouseButtonPressed(3) then
                    repeat
                        if IsMouseButtonPressed(1) then
                            repeat
                                MoveMouseRelative(0, RecoilControlStrength)
                                Sleep(DelayRate)
                            until not IsMouseButtonPressed(1)
                        end
                    until not IsMouseButtonPressed(3)
                end
            end
        else
            if IsMouseButtonPressed(3) then
                repeat
                    if IsMouseButtonPressed(1) then
                        repeat
                            MoveMouseRelative(0, RecoilControlStrength)
                            Sleep(DelayRate)
                        until not IsMouseButtonPressed(1)
                    end
                until not IsMouseButtonPressed(3)
            end
        end
    end
end
--| Código Feito Apenas Para Teste (Ta Funcionando Em Qualquer Jogo :) ) |--
